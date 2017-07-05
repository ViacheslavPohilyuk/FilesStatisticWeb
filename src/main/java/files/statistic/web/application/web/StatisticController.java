package files.statistic.web.application.web;

import files.statistic.web.application.model.LineStatistic;
import files.statistic.web.application.model.TextForm;
import files.statistic.web.application.service.DBService;

import files.statistic.web.application.model.TextFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by mac on 14.04.17.
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController {
    private DBService dbService;

    @Autowired
    public void setDbService(DBService dbService) {
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
                    .filter(f -> {
                        if ((f.getLinesStatistic().size() >= linesCount) && (f.getLinesStatistic().size() <= (linesCount + 50))) {
                            return true;
                        }
                        else if ((linesCount == 300) && (f.getLinesStatistic().size() >= linesCount)) {
                            return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        }
        return files;
    }

    @RequestMapping(value = "/addtext", method = RequestMethod.POST)
    public String saveTextStatistic(TextForm form) throws Exception {
        dbService.saveTextStatistic(form.getMessage());
        return "redirect:/";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String processUpload(@RequestPart("textFile") MultipartFile file) throws IOException {
        dbService.saveUploadedTextFileStatistic(file);
        return "redirect:/";
    }
}
