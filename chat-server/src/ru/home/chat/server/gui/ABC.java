package ru.home.chat.server.gui;

public class ABC {
    private volatile String currentLetter = "A";
    private final Object mon = new Object();

    public void printABC() {
        Thread threadA = new Thread(() -> {
            synchronized (mon) {
                boolean interrupted = false;
                for (int i = 0; i < 5; i++) {
                    if (Thread.currentThread().isInterrupted() || interrupted) {
                        break;
                    }
                    while (currentLetter != "A") {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            interrupted = true;
                        }
                    }
                    System.out.print(currentLetter);
                    currentLetter = "B";
                    mon.notifyAll();
                }
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (mon) {
                boolean interrupted = false;
                for (int i = 0; i < 5; i++) {
                    if (Thread.currentThread().isInterrupted() || interrupted) {
                        break;
                    }
                    while (currentLetter != "B") {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            interrupted = true;
                        }
                    }
                    System.out.print(currentLetter);
                    currentLetter = "C";
                    mon.notifyAll();
                }
            }
        });

        Thread threadC = new Thread(() -> {
            synchronized (mon) {
                boolean interrupted = false;
                for (int i = 0; i < 5; i++) {
                    if (Thread.currentThread().isInterrupted() || interrupted) {
                        break;
                    }
                    while (currentLetter != "C") {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            interrupted = true;
                        }
                    }
                    System.out.print(currentLetter);
                    currentLetter = "A";
                    mon.notifyAll();
                }
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
