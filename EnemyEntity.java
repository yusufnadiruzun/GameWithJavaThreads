
package bossfight;

public class EnemyEntity  extends Thread  implements PotencyCalculator {
 
    private int entityID;
    private int healthPoints;
    private int baseDamage;

    public EnemyEntity(int entityID, int healthPoint, int baseDamage) {
        this.entityID = entityID;
        this.healthPoints = healthPoint;
        this.baseDamage = baseDamage;
    }
    
    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getHealthPoint() {
        return healthPoints;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoints = healthPoint;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    @Override
    public void run() {
       
    }

    @Override
    public int dealDamage() {
       return getBaseDamage();
    }

    @Override
    public void takeDamage(int damage) {
        setHealthPoint(getHealthPoint()- damage);
    }    
}
