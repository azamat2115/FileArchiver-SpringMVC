package uz.azamat.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class HomeController {
    @GetMapping()
    public String display() {
        return "index";
    }

    @PostMapping("/upload")
    public void singleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String substring = originalFilename.substring(0, originalFilename.length() - 4);
        response.setHeader("Content-disposition", "attachment; filename=" + substring + ".zip");
        ZipEntry zipEntry = new ZipEntry(originalFilename);
        zipOut.putNextEntry(zipEntry);

        InputStream in = file.getInputStream();
        while (in.available() > 0) {
            int i = in.read();
            zipOut.write(i);
        }
        in.close();
        zipOut.closeEntry();
        zipOut.close();
    }
}
