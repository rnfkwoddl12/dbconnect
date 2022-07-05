package com.example.dbconnect.board.service;

import com.example.dbconnect.board.dto.BoardDto;
import com.example.dbconnect.board.dto.BoardFileDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BoardService {
    List<BoardDto> selectBoardList() throws Exception;


    void insertBoard(BoardDto board,MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    BoardDto selectBoardDetail(int boardIdx) throws Exception;
    void updateBoard(BoardDto board,MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
    void deleteBoard(int boardIdx) throws Exception;
    BoardFileDto selectBoardFileInformation(int idx , int boardIdx ) throws  Exception;
    void deleteBoardFile(int idx , int boardIdx) throws Exception;

}
