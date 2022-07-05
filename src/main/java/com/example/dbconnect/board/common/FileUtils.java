package com.example.dbconnect.board.common;

import com.example.dbconnect.board.dto.BoardFileDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileUtils {
    public List<BoardFileDto> parseFileInfo(int boardIndex , MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
            return null;
        }

        List<BoardFileDto> fileList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "images/"+ current.format(format);
        File file = new File(path);
        if(file.exists() == false){
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        String newFileName , orginaFileExtension, contentType;

        while (iterator.hasNext()){
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for(MultipartFile multipartFile : list){
                if(multipartFile.isEmpty() == false){
                    contentType = multipartFile.getContentType();
                    if(ObjectUtils.isEmpty(contentType)){
                        break;
                    }else {
                        if(contentType.contains("image/jpeg")){
                            orginaFileExtension =".jpg";

                        }else if(contentType.contains("image/png")){
                            orginaFileExtension =".png";
                        }else if(contentType.contains("image/gif")){
                            orginaFileExtension = ".gif";
                        }else {
                            break;
                        }
                    }

                    newFileName = Long.toString(System.nanoTime()) + orginaFileExtension;
                    BoardFileDto boardFile = new BoardFileDto();
                    boardFile.setBoardIdx(boardIndex);
                    boardFile.setFileSize(multipartFile.getSize());
                    boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                    boardFile.setStoredFilePath(path + "/" + newFileName);
                    fileList.add(boardFile);

                    file = new File(path + "/" + newFileName);
                    multipartFile.transferTo(file);

                }
            }
        }
        return fileList;
    }

}
