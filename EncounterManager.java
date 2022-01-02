/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bossfight;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.monitor.MonitorSettingException;

/**
 *
 * @author bahadir
 */
public class EncounterManager {

    Scanner s = new Scanner(System.in);
    private Object lock = new Object();
    private Tank tank = null;
    private DamageDealer damageDealer = null;
    private Healer healer = null;
    private EnemyEntity enemy = null;
    private int counter = 0;
    private boolean secondTemp = true;
    
    public void menu() {
        boolean temp = true;
        while (temp) {
            Printer.init(s);

            Printer.write("1) Register entities");
            Printer.write("2) Start encounter");
            Printer.write("3) Exit");

            String key = s.nextLine();
            switch (key) {
                case "1":
                    registerEntities(false);
                    startEncounter();
                    break;
                case "2":
                    registerEntities(true);
                    startEncounter();
                    temp = false;
                    break;
                case "3":
                    System.exit(0);
                    temp = false;
                    break;
            }
        }
    }

    /**
     * registers entities with auto param
     *
     *  @param isAutoCreate
     */
    public void startEncounter() {
        
        while (enemyIsAlive() && playerIsAlive()) {
            playerAttack();
            enemyAttack();
            healPlayer();
        }
        if(!enemyIsAlive()){
            Printer.write("The enemy is dead. The encounter has ended.");
        }
        if(!playerIsAlive()){
            Printer.write("The player is dead. The encounter has ended.");
        }
    }

    
    
    /**
     * register entities
     */
    public void registerEntities(boolean isAutoCreate) {
       Printer.init(s);
        if (!isAutoCreate) {
            while (tank == null || healer == null || damageDealer == null || enemy == null) {

                Printer.write("Select an entity to register:");
                if (tank == null) {
                    Printer.write("a) Tank");
                }
                if (healer == null) {
                    Printer.write("b) Healer");
                }
                if (damageDealer == null) {
                    Printer.write("c) Damage Dealer");
                }
                if (enemy == null) {
                    Printer.write("d) Enemy");
                }
                  String key = Printer.readString();
               

                    switch (key) {
                        case "a":
                            if (tank == null) {
                                registerTank(false);
                            }
                            break;

                        case "b":
                            if (healer == null) {
                                registerHealer(false);
                            }
                            break;

                        case "c":
                            if (damageDealer == null) {
                                registerDamageDealer(false);
                            }
                            break;

                        case "d":
                            if (enemy == null) {
                                spawnEnemy(false);
                            }
                            break;
                    }
                
            }
        } else {
            registerTank(true);
            registerDamageDealer(true);
            registerHealer(true);
            spawnEnemy(true);
        }
    }
    
    
    
    
    
    /**
     * Show HP states
     */
    public void printHPs() {

        Printer.write("======================");
        Printer.write("Entities’ HP");
        Printer.write("Tank" + ":" + tank.getHealthPoints());
        Printer.write("Damage Dealer" + ":" + damageDealer.getHealthPoints());
        Printer.write("Healer" + ":" + healer.getHealthPoints());
        Printer.write("Enemy" + ":" + enemy.getHealthPoint());
        System.out.println("======================");

    }

    
    
    
    
    /**
     * Creates game member as a Tank
     *
     * @param isAutoGeneration
     */
    public void registerTank(boolean isAutoGeneration) {
        if (!isAutoGeneration) {
            Printer.write("Healt: ");
            int health = s.nextInt();
            Printer.write("Damage: ");
            int damage = Printer.readInt();
            Printer.write("Defense: ");
            int defense = Printer.readInt();
            this.tank = new Tank(defense, "tank", 1, health, damage);
        } else {
            this.tank = new Tank(10, "tank", 1, 100, 10);
        }

    }

    
    
    /**
     * Creates game member as a Damage Dealer
     *
     * @param isAutoGeneration
     */
    public void registerDamageDealer(boolean isAutoGeneration) {
        if (!isAutoGeneration) {
            Printer.write("Healt: ");
            int health = Printer.readInt();
            Printer.write("Damage: ");
            int damage = Printer.readInt();
            Printer.write("Intelligence: ");
            int intelligence = Printer.readInt();
            this.damageDealer =  new DamageDealer(intelligence, "damageDealer", 2, health, damage);
        } else {
            this.damageDealer = new DamageDealer(7, "damageDealer", 2, 100, 10);
        }
    }

    /**
     * Creates game member as a Healler
     *
     * @param isAutoGeneration
     */
    public void registerHealer(boolean isAutoGeneration) {
        if (!isAutoGeneration) {
            Printer.write("Healt: ");
            int health = Printer.readInt();
            Printer.write("Damage: ");
            int damage = Printer.readInt();
            Printer.write("Mind: ");
            int mind = Printer.readInt();
            this.healer = new Healer(mind, "healer", 3, health, damage);
        } else {
            this.healer = new Healer(8, "healer", 3, 100, 10);
        }

    }

    public void spawnEnemy(boolean isAutoGeneration) {
        if (!isAutoGeneration) {
            Printer.write("Healt: ");
            int health = Printer.readInt();
            Printer.write("Damage: ");
            int damage = Printer.readInt();
            this.enemy = new EnemyEntity(4, health, damage);
        } else {
            this.enemy = new EnemyEntity(4, 100, 25);
        }
    }

    /**
     * Checks is enemy alive
     *
     * @return
     */
    public boolean enemyIsAlive() {

        if(enemy.getHealthPoint() <= 0){
            enemy.setHealthPoint(0);
            return false;
        }
        else{
            return true;
        }
        
    }

    /**
     * Checks is members alive
     *
     * @return
     */
    
    public boolean playerIsAlive(){
        if(tank.getHealthPoints() < 0){
            tank.setHealthPoints(0);
        }
        if(healer.getHealthPoints() < 0){
            healer.setHealthPoints(0);
        }
        if(damageDealer.getHealthPoints() < 0){
            damageDealer.setHealthPoints(0);
        }
        
       return ((tank.getHealthPoints() > 0) || (healer.getHealthPoints() > 0) || (damageDealer.getHealthPoints() > 0));
      
    }
    /**
     * This attack type deals damage to all members
     *
     * @return
     */
    public void groupWideAttack() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        tank.takeDamage(enemy.dealDamage());
                        damageDealer.takeDamage(enemy.dealDamage());
                        healer.takeDamage(enemy.dealDamage());
                        Printer.write("Enemy attacked all players ("+enemy.dealDamage()+" damage attack)");
                        playerIsAlive();
                        printHPs();
                        Thread.sleep(200);  
                        lock.notify();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalMonitorStateException ex) {
                        Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
        
      thread.start();
        
       
    }

    /**
     * Player attacks to enemy
     *
     * @param player
     * @return
     */
    public void playerAttack() {
        
        
         Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                         
                        enemy.takeDamage(tank.dealDamage());
                        Printer.write("Tank attacked the Enemy ("+damageDealer.dealDamage()+" damage attack)");
                        enemyIsAlive();
                        printHPs();
                        Thread.sleep(1000);
                        lock.wait();
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        enemy.takeDamage( damageDealer.dealDamage());
                        Printer.write("Damage Dealer attacked the Enemy ("+damageDealer.dealDamage()+" damage attack)");
                        enemyIsAlive();
                        printHPs();
                        Thread.sleep(500);
                        lock.notify();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        });
        if (tank.getHealthPoints() > 0 && enemyIsAlive()) {
            thread1.start();
        }
        if (damageDealer.getHealthPoints() > 0 && enemyIsAlive()) {
            thread2.start();
        }
        try {
            thread1.join();
             thread2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        if(!enemyIsAlive()){
         thread1.stop();
        }
        if(!enemyIsAlive()){
         thread2.stop();                   
        }
        
    }

    /**
     * Attacks and print log
     *
     * @param player
     * @return
     */
    public void enemyAttack() {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(1000);
                        tank.takeDamage(enemy.dealDamage());
                        Printer.write("Enemy attacked the tank ("+ enemy.dealDamage()+" damage attack)");
                        playerIsAlive();
                        printHPs();
                        counter++;
                         if(counter == 4){
                            counter = 0;
                            groupWideAttack();
                        }
                      
                        lock.notify();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                          Thread.sleep(1000);
                        
                        damageDealer.takeDamage(enemy.dealDamage());
                        Printer.write("Enemy attacked the Damage Dealer ("+ enemy.dealDamage()+" damage attack)");
                        playerIsAlive();
                        printHPs();
                        counter++;
                         if(counter == 4){
                            counter = 0;
                            groupWideAttack();
                        }
                      
                        lock.notify();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    try {       
                        Thread.sleep(1000);
                        healer.takeDamage(enemy.dealDamage());
                        Printer.write("Enemy attacked the tank ("+ enemy.dealDamage()+" damage attack)");
                        playerIsAlive();
                        printHPs();
                        counter++;
                        if(counter == 4){
                            counter = 0;
                            groupWideAttack();
                        }
                 
                        lock.notify();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalMonitorStateException w){}

                }
            }
        });
     
        
            
            if (tank.getHealthPoints() > 0 && enemyIsAlive()) {
                thread1.start();
            }else{
                thread1.stop();
            }
            if (damageDealer.getHealthPoints() > 0 && enemyIsAlive()) {
                thread2.start();
            }else{
                thread2.stop();
            }
            if (healer.getHealthPoints() > 0 && enemyIsAlive()) {
                thread3.start();
            }else{
                thread3.stop();
            }
            
            try {
                thread1.join();
                thread2.join();
                thread3.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
          
      
        

    }

    /**
     * Heals group member and print log
     *
     * @param player
     * @return
     */
    public void healPlayer() {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
   
                        tank.setHealthPoints(tank.getHealthPoints() + healer.getMind() + healer.getBaseDamage());
                        Printer.write("healer died in the tank ( heal "+ (healer.getMind() + healer.getBaseDamage())+" )");
                        printHPs();
                        Thread.sleep(1000);
                        lock.notify();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                       
                        damageDealer.setHealthPoints(damageDealer.getHealthPoints() + healer.getMind() + healer.getBaseDamage());
                        Printer.write("healer died in the Damage Dealer  ( heal "+ (healer.getMind() + healer.getBaseDamage())+" )");
                        printHPs();
                        Thread.sleep(1000);
                        lock.notify();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
       if(enemyIsAlive()) {
           if(tank.getHealthPoints() > 0){thread1.start();}
           if(damageDealer.getHealthPoints() > 0){thread2.start();}
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(EncounterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }else{ thread1.stop();thread2.stop();}
        

    }
}
