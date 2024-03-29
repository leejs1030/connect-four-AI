package com.leejs1030.connectfour.myexception;

import org.junit.Test;

public class WrongInputExceptionTest {
    
    @Test
    public void printErrorTest(){
        try{
            throw new WrongInputException(0);
        } catch(WrongInputException err){
            System.out.println("start");
            System.out.println(err);
            System.out.println(err.getMessage());
            System.out.println(err.toString());
            err.printStackTrace();
            System.out.println("end");
        }
    }

    @Test
    public void testWrongInputException() throws WrongInputException{
        System.out.println(new WrongInputException(0));
        System.out.println(new WrongInputException(1));
    }
}
