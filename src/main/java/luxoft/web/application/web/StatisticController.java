package luxoft.web.application.web;

import luxoft.web.application.service.HibernateDBService;
import luxoft.web.application.model.LineStatistic;

import luxoft.web.application.model.TextFile;
import luxoft.web.application.model.TextForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by mac on 14.04.17.
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController {
    private HibernateDBService dbService;

    @Autowired
    public void setDbService(HibernateDBService dbService) {
        this.dbService = dbService;
    }

    @RequestMapping(method = GET)
    public @ResponseBody
    Collection<TextFile> allFilesStatistic() {
        return dbService.getAllTextFiles();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public @ResponseBody
    List<LineStatistic> oneFileStatistic(@PathVariable long id) {
        return dbService.getTextFile(id).getLinesStatistic();
    }

    @RequestMapping(value = "/filter", method = GET)
    public @ResponseBody
    Collection<TextFile> statisticFilter(@RequestParam(value = "linesCount", required = false) Integer linesCount) {
        Collection<TextFile> files = dbService.getAllTextFiles();

        /* Filtering collections of TextFile objects by lines count */
        if (linesCount != null) {
            files = files.stream()
                    .filter(f -> ((f.getLinesStatistic().size() >= linesCount) && f.getLinesStatistic().size() <= (linesCount + 50)) || (f.getLinesStatistic().size() >= 300))
                    .collect(Collectors.toList());
        }
        return files;
    }

    @RequestMapping(value = "/addtext", method = RequestMethod.POST)
    public String saveTextStatistic(TextForm form) throws Exception {
        dbService.addTextStatistic(form.getMessage());
        return "redirect:/";
    }
}
