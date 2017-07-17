package files.statistic.web.application.web;

import files.statistic.web.application.model.TextFile;
import files.statistic.web.application.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by mac on 17.07.17.
 */
/*@RestController
@RequestMapping("/statistic/")
public class BunchStatisticController {
    @Autowired
    DBService dbService;

    Collection<TextFile> files;

    @RequestMapping(method = GET)
    public @ResponseBody
    Collection<TextFile> filesStatisticByBunch() {
        if (files == null)
            files = dbService.getAllTextFiles();


    }
}*/
