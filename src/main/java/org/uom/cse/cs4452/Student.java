package org.uom.cse.cs4452;

/**
 * @author Chanaka Lakmal
 * @date 21/6/2017
 * @since 1.0
 * <p>
 * This class is for Student thread who eat pizza while study and calls DeliveryGuys whenever pizzas are over
 */
public class Student implements Runnable {

    private int id;                     //for the identification of the student

    public Student(int index) {
        this.id = index;                //assign the id of the student while creating the instance
    }

    public void run() {
        try {
            while (true) {
                Main.lock.lock();               //ensure mutual exclusion of slicesCount

                if (Main.slicesCount == 0) {    //check for sliceCount == 0
                    if (!Main.hasOrdered) {     //check for someone has ordered
                        Main.order.signal();    //signal the deliver guy
                        Main.hasOrdered = true; //assign hasOrdered into true to ensure others will not call
                    }
                    System.out.println("Student " + this.id + " goes to sleep");
                    Main.deliver.await();       //await for deliver guy's signal after delivery
                }

                Main.lock.unlock();             //release the lock
                eatPizzaAndStudy();             //study while eating pizza
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is for implementation of pizza eating and study process
     *
     * @throws InterruptedException
     */
    private void eatPizzaAndStudy() throws InterruptedException {
        Main.slicesCount--;                     //decrement the count of pizza slices
        System.out.println("Student " + this.id + " takes a slice of pizza and study while eating");
        Thread.sleep(100);
    }

}
