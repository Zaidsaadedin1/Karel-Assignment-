import stanford.karel.*;
public class Homework extends SuperKarel {//the class
    private int beepers, beepersUsed, movesCounter, streets, avenues, stepsCounter, streetInitialPoint, avenuesInitialPoint, initialPoints, streetMidPoint, avenuesMidPoint;
    public Homework() {//Class constructors
        resetWorld();
    }
    private void resetWorld() {//To reset the world
        beepers = 1000;//we have 1000 beepers in the bag
        movesCounter = 0;//to count the moves
        avenues = 1;//y -axis
        streets = 1;//x -axis
        beepersUsed = beepers;//to calculate how many beepers we used
        stepsCounter = 1;//to count steps
        streetInitialPoint = 0;//to set initial point for street
        avenuesInitialPoint = 0;//to set initial point for avenues
        initialPoints = 0;// to set initial points for
        streetMidPoint = 0;//to set midpoint for street
        avenuesMidPoint = 0;//to set midpoint for avenues
    }
    public void move() {//To move
        super.move();
        movesCounter++;
    }
    public void putBeeper() {//To put a beeper
        super.putBeeper();
        beepersUsed--;
    }
    public void countStreets() {//The smallest streets length to achieve the goal the minimum streets length should be 7
        while (frontIsClear()) {
            streets++;
            move();
        }
    }
    public void countAvenues() {//The smallest avenues length to achieve the goal the minimum avenues length should be 7
        turnLeft();
        while (frontIsClear()) {
            avenues++;
            move();
        }
    }
    private void returnHome() {// to return karel to the start point and face the east
        while (!facingWest()) {
            turnLeft();
        }
        while (frontIsClear()) {
            move();
        }
        turnLeft();
        while (frontIsClear()) {
            move();
        }
        turnLeft();
    }
    private boolean checkIfBoundLengthIsEven(int bound) {//to check streets and avenues are even or odd
        return bound % 2 == 0;
    }
    private boolean checkIfBoundIsAreEqual() {//to check streets and avenues are equal
        return avenues == streets;
    }
    private boolean checkIfBoundIsLargeEnough() {//to check map is large enough
        return streets > 6 && avenues > 6;
    }
    private void drawLineToDivideTheMap() {
        putBeeper();//will start by placing a beeper
        while (frontIsClear()) {// Karel check while front is clear
            move();//He will move
            if (!beepersPresent()) {//If there is no beeper
                putBeeper();// will place a beeper
            }
        }
    }
    public void divideTheMap() {//to divide the outer champers in all cases odd or even
        /*A*/
        if (!checkIfBoundIsLargeEnough()) {//before karel start dividing the map in half will check if the map large enough
            resetWorld();//if bounds is small will reset the world
            showErrorMessage("The map is not suitable for the test it should be at least 7 avenues and 7 streets");//and show the message
            return;
        }
        /*B*/
            turnLeft();//turn left after count the avenues
            int middle = (streets / 2) + 1;//To set the middle point of the street
            int countSteps = 1;//To count the steps
            while (countSteps != middle) {//Move until reach the middle point os street
                move();
                countSteps++;
            }
            turnLeft();//then face the south
            drawLineToDivideTheMap();//To draw Line To Divide The streets once
        if (!checkIfBoundLengthIsEven(streets)) {//then karel will check is street odd
            returnHome();//if it's odd he will return home
        } else {//else streets are even
            turnLeft();//will turn left
            move();//then move
            turnLeft();//then face the south
            drawLineToDivideTheMap();//To draw Line To Divide The streets twice
            returnHome();//after he split the middle of the street will return home to start splitting the avenues
        }
        countSteps = 1;//we will reset the steps counter
        turnLeft();//turn left from the home point
        /*C*/
        if (checkIfBoundLengthIsEven(avenues)) {//set the middle point of the avenues if its even
            middle = (avenues / 2);
        }
        else {//set the middle point of the avenues if it's odd
            middle = (avenues / 2) + 1;
        }
        while (countSteps != middle) {//Move until reach the middle point os street
            move();//move
            countSteps++;//increment steps counter
        }
        turnRight();//instead of make karel move left 3 times I choose turn right to face east
        drawLineToDivideTheMap();//To draw Line To Divide The avenues once
        if (!checkIfBoundLengthIsEven(avenues)) {//then karel will check is avenues odd
            returnHome();//if it's true will return home
        } else {//if avenues is even
            turnLeft();//turn left
            move();//then move
            turnLeft();//then turn left to face west
            drawLineToDivideTheMap();//To draw Line To Divide The avenues twice
            returnHome();//then return home to start draw the inner champers
        }
    }
    private void setTheValuesToDrawInnerChampersIfBothOddOrEvenAndAvenuesBiggerThanStreets() {
        move();//move
        turnLeft();//turn left
        if (!checkIfBoundLengthIsEven(streets)) {//midpoint of the street if odd
            streetMidPoint = (streets / 2) + 1;
        }
        else {//midpoint of the street if even
            streetMidPoint = (streets / 2);
        }
        streetInitialPoint = 2;// street initial point
        initialPoints = streetMidPoint - streetInitialPoint;//initialPoints
        if (!checkIfBoundLengthIsEven(avenues)) {//midpoint of the avenues if odd
            avenuesMidPoint = (avenues / 2) + 1;
        }
        else { //midpoint of the avenues if even
            avenuesMidPoint = (avenues / 2);
        }
        avenuesInitialPoint = avenuesMidPoint - initialPoints;//avenues initial point
        while (stepsCounter != avenuesInitialPoint) {//karel move until reach avenues Initial Point
            move();//move
            stepsCounter++;//increment steps counter
        }
    }
    private void setTheValuesToDrawInnerChampersIfBothOddOrEvenAndStreetsBiggerThanAvenues() {
        if (!checkIfBoundLengthIsEven(avenues)) {//midpoint of the avenues odd
            avenuesMidPoint = (avenues / 2) + 1;
        }
        else {//midpoint of the avenues even
            avenuesMidPoint = (avenues / 2);
        }
        avenuesInitialPoint = 2;//avenues initial point
        initialPoints = avenuesMidPoint - avenuesInitialPoint;//initialPoints
        if (!checkIfBoundLengthIsEven(streets)) {//midpoint of the street
            streetMidPoint = (streets / 2) + 1;
        }
        else {//midpoint of the street
            streetMidPoint = (streets / 2);
        }
        streetInitialPoint = streetMidPoint - initialPoints;// street initial point
        while (stepsCounter != streetInitialPoint) {//karel move until reach street Initial Point
            move();//move
            stepsCounter++;//increment steps counter
        }
        turnLeft();//turn left
        move();//move
    }
    private void drawInnerChampersIfBothOddOrEven(int lengthOfTheSquareSide) {
        for (int i = 0; i < 4; i++) {
            while (stepsCounter != lengthOfTheSquareSide) {//karel move until reach street initialPoints
                if (!beepersPresent()) {//If there is no beeper
                    putBeeper();//place beeper
                    move();//move
                    stepsCounter++;//increment steps counter
                } else {
                    move();//move if there is a beeper
                    stepsCounter++;//increment steps counter
                }
            }
            stepsCounter = 1;//reset steps counter
            turnLeft();//turn left
            move();//move
            turnLeft();//turn left
            turnLeft();//turn left
            move();//move
        }
    }
    private void drawInnerChampersIfAvenuesOddAndStreetsEvenOrAvenuesEvenAndStreetsOddWitherStreetsLongerOrAvenues(int lengthOfTheSquareSideForAvenues,int lengthOfTheSquareSideForStreets) {
        for (int i = 0; i < 2; i++) {//To draw the inner champers if streets and avenues are odd ,whither streets longer or avenues
            stepsCounter=1;
            while (stepsCounter != lengthOfTheSquareSideForAvenues) {//karel move until reach street initialPoints
                if (!beepersPresent()) {//If there is no beeper
                    putBeeper();//place beeper
                    move();//move
                    stepsCounter++;//increment steps counter
                } else {
                    move();//move if there is a beeper
                    stepsCounter++;//increment steps counter
                }
            }
            turnLeft();//turn left
            turnLeft();//turn left
            turnLeft();//turn left
            stepsCounter=1;
            while (stepsCounter != lengthOfTheSquareSideForStreets) {//karel move until reach street initialPoints
                if (!beepersPresent()) {//If there is no beeper
                    putBeeper();//place beeper
                    move();//move
                    stepsCounter++;//increment steps counter
                } else {
                    move();//move if there is a beeper
                    stepsCounter++;//increment steps counter
                }
            }
            turnLeft();//turn left
            turnLeft();//turn left
            turnLeft();//turn left
            stepsCounter = 1;//reset steps counter
        }
    }
    public void drawInnerChampers() {//To draw the inner champers for all cases whither streets and avenues are equal or not or even or odd
        /*1*/
        if (checkIfBoundIsAreEqual()) {//To draw the inner champers  streets and avenues are equal after we created the outer champers and karel in the home point facing east
            move();//move
            turnLeft();//turn left
            move();//move
            for (int i = 0; i < 4; i++) {// for loop to draw inner champers 4 times
                while (frontIsClear()) {// Karel check while front is clear
                    if (!beepersPresent()) {//If there is no beeper
                        putBeeper();// will place a beeper
                        move();//move
                        stepsCounter++;//increment steps counter
                    } else {//If there is a beeper
                        move();//he just will move
                        stepsCounter++;//increment steps counter
                    }
                }
                turnLeft();//turn left
                turnLeft();//turn left      //these are made to make karel face east when he is facing south
                move();//move               //then face north when he is facing west and the apostate
                turnLeft();//turn left
            }
            stepsCounter = 1;//reset steps counter
        } else {// if bounds not equal
            /*2*/
            if (!checkIfBoundLengthIsEven(avenues) && !checkIfBoundLengthIsEven(streets)) {//To draw the inner champers if streets and avenues are odd
                /*A*/
                if (avenues > streets) {//check if avenues longer
                    setTheValuesToDrawInnerChampersIfBothOddOrEvenAndAvenuesBiggerThanStreets();
                    /*B*/
                } else {//check if streets longer
                    setTheValuesToDrawInnerChampersIfBothOddOrEvenAndStreetsBiggerThanAvenues();
                }
                stepsCounter = 1;//reset steps counter
                drawInnerChampersIfBothOddOrEven((initialPoints * 2) + 1);//to draw inner champers
            }
            /*3*/
            if (checkIfBoundLengthIsEven(avenues) && checkIfBoundLengthIsEven(streets)) {//To draw the inner champers if streets and avenues are odd
                /*A*/
                if (avenues > streets) {//check if avenues longer
                    setTheValuesToDrawInnerChampersIfBothOddOrEvenAndAvenuesBiggerThanStreets();
                    /*B*/
                } else {//To create the logic to pass it to the for loop and draw inner champers if streets and avenues even when street longer
                    setTheValuesToDrawInnerChampersIfBothOddOrEvenAndStreetsBiggerThanAvenues();
                }
                stepsCounter = 1;//reset steps counter
                drawInnerChampersIfBothOddOrEven((initialPoints * 2) + 2);//to draw inner champers
            }
            /*4*/
            if (!checkIfBoundLengthIsEven(avenues) && checkIfBoundLengthIsEven(streets)) {//To draw the inner champers if streets even and avenues are odd
                /*A*/
                if (avenues > streets) {//check if avenues longer
                    setTheValuesToDrawInnerChampersIfBothOddOrEvenAndAvenuesBiggerThanStreets();
                    /*B*/
                } else {//To create the logic to pass it to the for loop and draw inner champers if streets and avenues even when street longer
                    setTheValuesToDrawInnerChampersIfBothOddOrEvenAndStreetsBiggerThanAvenues();
                }
                drawInnerChampersIfAvenuesOddAndStreetsEvenOrAvenuesEvenAndStreetsOddWitherStreetsLongerOrAvenues((initialPoints * 2) + 1, (initialPoints * 2) + 2);

                }
            }
            /*5*/
            if (checkIfBoundLengthIsEven(avenues) && !checkIfBoundLengthIsEven(streets)) {//To draw the inner champers if streets odd and avenues  even
                /*A*/
                if (avenues > streets) {//check if avenues longer
                    setTheValuesToDrawInnerChampersIfBothOddOrEvenAndAvenuesBiggerThanStreets();
                    /*B*/
                } else {//To create the logic to pass it to the for loop and draw inner champers if streets and avenues even when street longer
                    setTheValuesToDrawInnerChampersIfBothOddOrEvenAndStreetsBiggerThanAvenues();
                }
                stepsCounter = 1;//reset steps counter
                drawInnerChampersIfAvenuesOddAndStreetsEvenOrAvenuesEvenAndStreetsOddWitherStreetsLongerOrAvenues((initialPoints * 2) + 2, (initialPoints * 2) + 1);
                }
            }
    public void run() {//Run method
        countStreets();//count streets length
        countAvenues();//count Avenues length
        divideTheMap();//to call the divide the map method
        drawInnerChampers();//to call the draw Inner Champers method
        System.out.println("moves: " + movesCounter + "  " + "beepers used: " + (beepers - beepersUsed) + "  " + "avenues: " + avenues + "  " + "streets: " + streets);//print result
        resetWorld();//reset the world
    }
}
