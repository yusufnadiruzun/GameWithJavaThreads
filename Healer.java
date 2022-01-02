
package bossfight;

public class Healer extends Player{
    
    private int mind;

    public Healer(int mind, String role, int entityID, int healtyPoints, int baseDamage) {
        super(role, entityID, healtyPoints, baseDamage);
        this.mind = mind;
    }

    public int getMind() {
        return mind;
    }

    public void setMind(int mind) {
        this.mind = mind;
    }

    @Override
    public int dealDamage() {
        return getBaseDamage();
    }

    @Override
    public void takeDamage(int damage) {
        setHealthPoints(getHealthPoints() - damage);
    }  
}
