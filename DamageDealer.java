
package bossfight;

public class DamageDealer extends Player  {
    
    
    private int intelligent;

    public DamageDealer(int intelligent, String role, int entityID, int healtyPoints, int baseDamage) {
        super(role, entityID, healtyPoints, baseDamage);
        this.intelligent = intelligent;
    }

    public int getIntelligent() {
        return intelligent;
    }

    public void setIntelligent(int intelligent) {
        this.intelligent = intelligent;
    }

    @Override
    public int dealDamage() { 
        return (getBaseDamage()+intelligent);
    }

    @Override
    public void takeDamage(int damage) {
        setHealthPoints(getHealthPoints()- damage);
    }
}
