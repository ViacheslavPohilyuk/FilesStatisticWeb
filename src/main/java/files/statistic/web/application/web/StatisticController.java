package files.statistic.web.application.web;

import files.statistic.web.application.model.LineStatistic;
import files.statistic.web.application.model.StatisticFilter;
import files.statistic.web.application.model.TextForm;
import files.statistic.web.application.service.DBService;

import files.statistic.web.application.model.TextFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by mac on 14.04.17.
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController {
    private DBService dbService;

    private int runtimeFilesCount;
    private Long newFileId;
    private LinkedList<TextFile> files;

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    @RequestMapping(method = GET)
    public @ResponseBody
    List<TextFile> allFilesStatistic() {
        if (files == null) {
            files = new LinkedList<>(dbService.getAllTextFiles());
            runtimeFilesCount = files.size();
            System.err.println("Load all files!");
        }
        else if(files.size() != runtimeFilesCount) {
            files.addFirst(dbService.getTextFile(newFileId));
            System.err.println("Load new file!");
        }
        return files;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public @ResponseBody
    List<LineStatistic> oneFileStatistic(@PathVariable long id) {
        return dbService.getTextFile(id).getLinesStatistic();
    }

    @RequestMapping(value = "/filter", method = GET)
    public @ResponseBody
    Collection<TextFile> statisticFilter(StatisticFilter filt) {
        return filt.filtering(dbService.getAllTextFiles());
    }

    @RequestMapping(value = "/addtext", method = RequestMethod.POST)
    public String saveTextStatistic(TextForm form) throws Exception {
        newFileId = dbService.saveTextStatistic(form.getMessage());
        runtimeFilesCount++;
        return "redirect:/";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String processUpload(@RequestPart("textFile") MultipartFile file) throws IOException {
        newFileId = dbService.saveUploadedTextFileStatistic(file);
        runtimeFilesCount++;
        return "redirect:/";
    }
}
