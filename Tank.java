
package bossfight;

public class Tank extends Player  {
    private int defense;

    public Tank(int defense, String role, int entityID, int healtyPoints, int baseDamage) {
        super(role, entityID, healtyPoints, baseDamage);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void run(EnemyEntity e) {
    
    }
  
    @Override
    public int dealDamage() {
        return getBaseDamage();
    }

    @Override
    public void takeDamage(int damage) {
       setHealthPoints(getHealthPoints()+ getDefense() - damage);
    }
}
