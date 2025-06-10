package com.pluralsight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.lalyos.jfiglet.FigletFont;

import java.io.IOException;

public class Main {

    final static Logger logger = (Logger) LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        logger.info("This is an INFO log");
        logger.warn("This is a warning");

        String asciiArt = FigletFont.convertOneLine("java dependencies");
        System.out.println(asciiArt);

    }
}