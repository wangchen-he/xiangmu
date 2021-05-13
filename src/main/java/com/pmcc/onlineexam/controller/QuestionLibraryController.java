/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/20
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.controller;

import com.pmcc.onlineexam.model.QuestionLibrary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/exam/questionlibrary")
public class QuestionLibraryController {

    public List<QuestionLibrary> query(){

        return null;
    }
}
