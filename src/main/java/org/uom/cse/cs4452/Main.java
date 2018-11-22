package org.uom.cse.cs4452;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Chanaka Lakmal
 * @date 21/6/2017
 * @since 1.0
 * <p>
 * This class is for define the shared variables and start all the threads
 */
public class Main {

    static Lock lock = new ReentrantLock();             //protects “slicesCount” associated with both condition variables
    static Condition order = lock.newCondition();       //condition for an order has been placed
    static Condition deliver = lock.newCondition();     //condition for a delivery has been made
    static boolean hasOrdered = false;                  //flag for first one to order
    static int slicesCount = 0;                         //remaining count of pizza slices

    final static int slicesForPizza = 10;               //pre-define no of pizza slices into 10
    final static int studentCount = 20;                 //pre-define no of student count to 20

    public static void main(String[] args) {

        /* create a instance of DeliveryGuy and start the thread */
        new Thread(new DeliveryGuy()).start();

        /* create a studentCount no of Student instances and start their threads */
        for (int i = 0; i < studentCount; i++) {
            new Thread(new Student(i)).start();
        }

    }
}
