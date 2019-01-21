package com.company;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

/*
спрашиваем жеаемый размер поля и создаём массив чаров со сторонами указанного пользователем размера
 */
    private static char [][] start (){
        System.out.println("Игра Крестики - Нолики");
        System.out.println("Введите желаемый размер поля");
        int fieldSize = sc.nextInt();
        char[][] x= new char[fieldSize][fieldSize];
        for (int i = 0; i <fieldSize ; i++) {
            for (int j = 0; j <fieldSize ; j++) {
                x[i][j]='.';
            }
        }
        return x;
    }

/*
спрашиваем координаты желаемого хода игрока
 */

     private static char [][] player_turn(char [][]gameField){
        int x;
        int y;
        do {
            System.out.println("Введите координаты X и Y для установки крестика ");
            x = sc.nextInt()-1;
            y = sc.nextInt()-1;

          } while (gameField[y][x] != '.');
            gameField[y][x] = 'X';
            return gameField;

        }

 /*
   ставим ход компьютера в полученную от ИИ точку
*/
    private static char[][] ai_Turn(char[][]gameField,int winval){
        int[] position = aI_Engine(gameField,winval);
        int x =position[1];
        int y= position[0];

        gameField[y][x] = 'O';
        return gameField;

    }

/*
вычисляем точку установки хода компьютера с целью прерввать линию игрока либо рандомную точку если игрок далёк от победы
 */
    private static int[] aI_Engine(char [][]gamefield,int winVal){

        int []playerScore=new int[(gamefield.length*2+2)];
        int []aITurnPosition=new int [2];
        for (int i = 0; i <gamefield.length ; i++) {
            switch (gamefield[i][i]){
                case 'X': playerScore[playerScore.length-1]++;

                    break;
                case 'O': playerScore[playerScore.length-1]=0;
                    break;
                case '.': playerScore[playerScore.length-1]=0;
                    break;

            }

if (playerScore[playerScore.length-1] == winVal-1) {
    aITurnPosition[0]=i+1;
    aITurnPosition[1]=i+1;
    return aITurnPosition;
}

            for (int j = 0; j <gamefield.length ; j++) {

                switch (gamefield[i][j]){
                    case 'X': playerScore[i]++;
                        break;
                    case 'O': playerScore[i]=0;
                        break;
                    case '.': playerScore[i]=0;
                       break;
                }
                if (playerScore[i] == winVal-1) {
                    aITurnPosition[0]=i;
                    aITurnPosition[1]=j+1;
                    return aITurnPosition;
                }
                switch (gamefield[j][i]){
                    case 'X': playerScore[i+(playerScore.length/2-1)]++;

                        break;
                    case 'O': playerScore[i+(playerScore.length/2-1)]=0;
                        break;
                    case '.': playerScore[i+(playerScore.length/2-1)]=0;
                        break;
                }
                if (playerScore[i+(playerScore.length/2-1)] == winVal-1) {
                    aITurnPosition[0]=j+1;
                    aITurnPosition[1]=i;
                    return aITurnPosition;
                }

            }
        }

        for (int i = 0, j = gamefield.length-1; i <gamefield.length; i++,j--) {

            switch (gamefield[i][j]) {
                case 'X':
                    playerScore[playerScore.length - 2]++;
                    break;
                case 'O':
                    playerScore[playerScore.length - 2] = 0;

                case '.':
                    playerScore[playerScore.length - 2] = 0;

                    break;

            }
            if (playerScore[playerScore.length - 2] == winVal - 1) {
                aITurnPosition[0] = i+1;
                aITurnPosition[1] = j-1;
                return aITurnPosition;

            }
        }
        int x;
        int y;
        do {
            x = (int) (Math.random()*gamefield.length);
            y = (int) (Math.random()*gamefield.length);

        } while (gamefield[y][x] != '.');
          aITurnPosition[0]=y;
          aITurnPosition[1]=x;
        return aITurnPosition;



    }
/*
проверяем есть ли победитель
 */
    private static int isWin(char[][]gamefield , int winVal){
        int []playerScore=new int[(gamefield.length*2+2)];
        int []aIScore = new int[(gamefield.length*2+2)] ;
        for (int i = 0; i <gamefield.length ; i++) {
            switch (gamefield[i][i]){
                case 'X': playerScore[playerScore.length-1]++;
                    aIScore[aIScore.length-1]=0;
                    break;
                case 'O': aIScore[aIScore.length-1]++;
                    playerScore[playerScore.length-1]=0;
                    break;
                case '.': playerScore[playerScore.length-1]=0;
                    aIScore[aIScore.length-1]=0;
                    break;

            }

            for (int w = 0; w <playerScore.length ; w++) {
                if (playerScore[w]==winVal) return 1;
                else if (aIScore[w]==winVal) return 2;


            }
            for (int j = 0; j <gamefield.length ; j++) {

                switch (gamefield[i][j]){
                    case 'X': playerScore[i]++;
                        aIScore[i]=0;
                    break;
                    case 'O': aIScore[i]++;
                        playerScore[i]=0;
                    break;
                    case '.': playerScore[i]=0;
                        aIScore[i]=0;
                        break;
                }
                switch (gamefield[j][i]){
                    case 'X': playerScore[i+(playerScore.length/2-1)]++;
                        aIScore[i+(aIScore.length/2-1)]=0;
                        break;
                    case 'O': aIScore[i+(aIScore.length/2-1)]++;
                        playerScore[i+(playerScore.length/2-1)]=0;
                        break;
                    case '.': playerScore[i+(playerScore.length/2-1)]=0;
                        aIScore[i+(playerScore.length/2-1)]=0;
                        break;
                }
                for (int w = 0; w <playerScore.length ; w++) {
                    if (playerScore[w]==winVal) return 1;
                    else if (aIScore[w]==winVal) return 2;


                }

            }
        }

        for (int i = 0, j = gamefield.length-1; i <gamefield.length; i++,j--) {

            switch (gamefield[i][j]){
                case 'X': playerScore[playerScore.length-2]++;
                    aIScore[aIScore.length-2]=0;
                    break;
                case 'O': aIScore[aIScore.length-2]++;
                    playerScore[playerScore.length-2]=0;
                    break;
                case '.': playerScore[aIScore.length-2]=0;
                    aIScore[aIScore.length-2]=0;
                    break;

            }
            for (int w = 0; w <playerScore.length ; w++) {
                if (playerScore[w]==winVal) return 1;
                else if (aIScore[w]==winVal) return 2;


            }
        }

        for (int i = 0; i <playerScore.length ; i++) {
            if (playerScore[i]==winVal) return 1;
            else if (aIScore[i]==winVal) return 2;


        }
        System.out.println();
        return 3;
    }


/*
печатаем поле игры
 */
    private static void printField(char[][] gameField){

        for (int z = 0; z <=gameField.length ; z++) {
            System.out.print((z)+" ");
        }
        System.out.println();
            for (int i = 0; i <gameField.length ; i++) {
                System.out.print(i+1+" ");
                for (int j = 0; j <gameField.length ; j++) {
                    System.out.print(gameField[i][j]+" ");

                }
                System.out.println();
            }

    }

    /*
    инициализируем
    спрашиваем сколько фишек для победы
    начинаем цикл :
    печатаем поле
    ход игрока
    проверка на победу игрока
    ход компа
    проверка на победу компа
     */
    private static void game() {
        char[][] gameField = start();
        System.out.println("Введите количество фишек для выигрыша");
        int winVal = sc.nextInt();
        do {
            printField(gameField);
            gameField = player_turn(gameField);
            if (isWin(gameField, winVal) == 1) {
                System.out.println("Вы победили");
                break;
            }


            gameField = ai_Turn(gameField, winVal);
            if (isWin(gameField, winVal) == 2) {
                System.out.println("AI победил");
                break;
            }
        }
        while (isWin(gameField,winVal)==3);

    }
    public static void main(String[] args) {
     game();

    }
}
