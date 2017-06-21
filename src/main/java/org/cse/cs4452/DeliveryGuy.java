package org.cse.cs4452;

/**
 * @author Chanaka Lakmal
 * @date 21/6/2017
 * @since 1.0
 * <p>
 * This class is for the DeliveryGuy thread which delivers the pizza and wakeup all the Student threads
 */
public class DeliveryGuy implements Runnable {

    public void run() {
        try {
            while (true) {
                Main.lock.lock();                           //ensure mutual exclusion of slicesCount
                Main.order.await();                         //wait for some student to call
                Main.slicesCount = Main.slicesForPizza;     //reset the slicesCount into slicesForPizza
                Main.hasOrdered = false;                    //assign hasOrdered into false to ensure anyone can call
                deliverAndWakeUp();                         //deliver and wakeup all the students
                Main.lock.unlock();                         //release the lock
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is for implementation of deliver and wakeup process
     * The delivery guy deliver pizza and signalAll the students to wakeup who are awaits for the pizza
     *
     * @throws InterruptedException
     */
    private void deliverAndWakeUp() throws InterruptedException {
        System.out.println("DeliveryGuy delivers a pizza and wakes up all the students");
        Main.deliver.signalAll();                           //signal all the students who are waiting for pizza
        Thread.sleep(1000);
    }
}
