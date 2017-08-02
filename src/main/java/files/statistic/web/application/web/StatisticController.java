package files.statistic.web.application.web;

import files.statistic.web.application.model.LineStatistic;
import files.statistic.web.application.model.form.StatisticFilter;
import files.statistic.web.application.model.form.TextForm;

import files.statistic.web.application.model.TextFile;
import files.statistic.web.application.data.StatisticDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by mac on 14.04.17.
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private StatisticDataService statisticDataService;

    @RequestMapping(method = GET)
    public @ResponseBody
    Collection<TextFile> allFilesStatistic() {
        return statisticDataService.getAllTextFiles();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public @ResponseBody
    List<LineStatistic> oneFileStatistic(@PathVariable long id) {
        return statisticDataService.getTextFile(id).getLinesStatistic();
    }

    @RequestMapping(value = "/filter", method = GET)
    public @ResponseBody
    Collection<TextFile> statisticFilter(StatisticFilter filt) {
        return filt.filtering(statisticDataService.getAllTextFiles());
    }

    @RequestMapping(value = "/addtext", method = RequestMethod.POST)
    public String saveTextStatistic(TextForm form) throws Exception {
        statisticDataService.saveTextStatistic(form.getMessage());
        return "redirect:/";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String processUpload(@RequestPart("textFile") MultipartFile file) throws IOException {
        statisticDataService.saveUploadedTextFileStatistic(file);
        return "redirect:/";
    }
}
