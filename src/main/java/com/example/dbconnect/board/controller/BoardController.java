package com.example.dbconnect.board.controller;


import com.example.dbconnect.board.dto.BoardDto;
import com.example.dbconnect.board.dto.BoardFileDto;
import com.example.dbconnect.board.service.BoardService;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class BoardController {


    @Autowired
    private BoardService boardService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/board/openBoardList.do")
    public ModelAndView openBoardList() throws Exception {

        log.debug("openBoardList");
        log.trace("trace level log");
        log.debug("debug level log");

        log.info("info level log");
        log.warn("warm level log");
        log.error("error level log");


        ModelAndView mv = new ModelAndView("/board/boardList");
        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);
        return mv;

    }

    @RequestMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception {
        return "/board/boardWrite";
    }

    @RequestMapping("/board/insertBoard.do")
    public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardService.insertBoard(board, multipartHttpServletRequest);
        return "redirect:/board/openBoardList.do";

    }

    @RequestMapping("/board/openBoardDetail.do")
    public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception {
        ModelAndView mv = new ModelAndView("/board/boardDetail");
        BoardDto board = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);
        return mv;

    }

    @RequestMapping("/board/updateBoard.do")
    public String updateBoard(BoardDto board,MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardService.updateBoard(board,multipartHttpServletRequest);
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/deleteBoard.do")
    public String deleteBoard(@RequestParam int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/downloadBoardFile.do")
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {

        BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
        if (ObjectUtils.isEmpty(boardFile) == false) {
            String fileName = boardFile.getOriginalFileName();
            byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
            response.setContentType("application/octet-stream");
            response.setContentLength(files.length);
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
            response.getOutputStream().write(files);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }

    }
    @RequestMapping("/board/deleteBoardFile.do")
    public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx)throws Exception{
        boardService.deleteBoardFile(idx,boardIdx);
        return "redirect:/board/openBoardDetail.do?boardIdx="+boardIdx;

    }




}