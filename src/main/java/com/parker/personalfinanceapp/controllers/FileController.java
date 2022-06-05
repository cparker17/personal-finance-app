package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.dto.FileNameWrapper;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileService fileService;

    @RequestMapping()
    public String viewFileList(Model model, Authentication auth) {
        model.addAttribute("filenames", fileService.getAllFileNames(UserFactory.createUser(auth).getId()));
        return "file-list";
    }

    @PostMapping("/add")
    public String addFile(Authentication auth, @RequestParam MultipartFile formFile) throws IOException {
        fileService.saveFile(UserFactory.createUser(auth).getId(), formFile);
        return "redirect:/files";
    }

    @RequestMapping("/view")
    public String viewFile(Model model, Authentication auth, @ModelAttribute FileNameWrapper fileNameWrapper) {
        model.addAttribute("file",
                fileService.getFile(UserFactory.createUser(auth).getId(), fileNameWrapper.getFileName()));
        return "file-view";
    }

    @RequestMapping("/delete")
    public String deleteFile(Authentication auth, @ModelAttribute FileNameWrapper fileNameWrapper) {
        fileService.deleteFile(UserFactory.createUser(auth).getId(), fileNameWrapper.getFileName());
        return "redirect:/files";
    }
}
