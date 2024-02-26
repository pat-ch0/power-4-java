import java.util.EnumMap;

public class Cellule {
    private Couleur c;
    private EnumMap<Direction, Cellule> voisins = new EnumMap<Direction, Cellule>(Direction.class);


    public Cellule(Couleur cur){
        this.c = cur;
    }

    public Couleur getColor(){
        return this.c;
    }

    public void setColor(Couleur cou){
        this.c = cou;
    }

    public EnumMap<Direction, Cellule> getVois(){
        return this.voisins;
    }

    public void setVois(EnumMap<Direction, Cellule> h){
        this.voisins = h;
    }

    public Direction oppositeOf(Direction d){
        if(d == Direction.HAUT){
            return Direction.BAS;
        }
        else if(d == Direction.BAS){
            return Direction.HAUT;
        }
        else if(d == Direction.GAUCHE){
            return Direction.DROITE;
        }
        else{
            return Direction.GAUCHE;
        }
    }

    public void addNeig(Cellule other, Direction d){
        this.voisins.put(d, other);
        other.voisins.put(this.oppositeOf(d), this);
    }

    public int check(Direction D){ //Vertical et Horizontale
        if(this.getVois().get(D) != null){
            if(this.getColor() == this.getVois().get(D).getColor()){
                return 1 + this.getVois().get(D).check(D);
            }
            else {
                return 0;
            }
        }
        else {
            return 0;
        }
    }

    public int check(Direction D1, Direction D2){ // Diagonales
        if((this.getVois().get(D1)) != null){
            if(this.getVois().get(D1).getVois().get(D2) != null){
                if(this.getVois().get(D1).getVois().get(D2).getColor() == this.getColor()){
                    return 1 + this.getVois().get(D1).getVois().get(D2).check(D1,D2);
                }
                else {
                    return 0;
                }
            }
            else{
                return 0;
            }
        }
        else {
            return 0;
        }
    }

    public Boolean check(){ 
        //Verification horizontale
        if (this.check(Direction.GAUCHE) + this.check(Direction.DROITE) >=3){
            return true;
        }
        //Verification verticale
        else if(this.check(Direction.HAUT) + this.check(Direction.BAS) >=3){
            return true;
        }
        // Verification diagonale : \
        else if(this.check(Direction.HAUT, Direction.GAUCHE) + this.check(Direction.BAS, Direction.DROITE) >=3){
            return true;
        } 
        // Verification diagonale : /
        else if(this.check(Direction.HAUT, Direction.DROITE) + this.check(Direction.BAS, Direction.GAUCHE) >=3){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        String str;
        if (this.c == Couleur.O){
            str = " " + "\033[0;34m";
        }
        else if (this.c == Couleur.R){
            str = "\033[1;31mO" + "\033[0;34m";
        }
        else {
            str = "\033[1;33mO" + "\033[0;34m";
        }
        return str;
    }
}
