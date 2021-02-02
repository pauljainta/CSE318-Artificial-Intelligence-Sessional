import java.util.Comparator;
import java.util.List;

public class Variable {
    int value;
    int row, col;
    List<Integer> domain;
    int dynamicDegree;

    public int getDynamicDegree() {
        return dynamicDegree;
    }

    public void setDynamicDegree(int dynamicDegree) {
        this.dynamicDegree = dynamicDegree;
    }

    public Variable(int value) {
        this.value = value;
    }

    public Variable(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;

    }

    public List<Integer> getDomain() {
        return domain;
    }

    public void setDomain(List<Integer> domain) {
        this.domain = domain;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }


    //comparator for sorting smallest domain wise




}
class DomainComp implements Comparator<Variable>{

    @Override
    public int compare(Variable v1, Variable v2) {
        if(v1.domain.size() < v2.domain.size()){
            return -1;
        } else if (v1.domain.size()>v2.domain.size()){
            return 1;
        }
        return 0;
    }
}

class DynamicDegreeComp implements Comparator<Variable>{

    @Override
    public int compare(Variable v1, Variable v2) {
        if(v1.getDynamicDegree() < v2.getDynamicDegree()){
            return 1;
        } else if (v1.getDynamicDegree()>v2.getDynamicDegree()){
            return -1;
        }
        return 0;
    }
}

class Breluz implements Comparator<Variable>{

    @Override
    public int compare(Variable v1, Variable v2) {

        if(v1.getDomain().size()==v2.getDomain().size())
        {
            if(v1.getDynamicDegree()>v2.getDynamicDegree()) return -1;
            else if(v1.getDynamicDegree()<v2.getDynamicDegree()) return 1;
            return 0;
        }
        else if(v1.getDomain().size()<v2.getDomain().size())
        {
            return -1;
        }
        return 1;

    }
}


class Domddeg implements Comparator<Variable>{

    @Override
    public int compare(Variable v1, Variable v2) {

       float DomsizeToForwarddegree1=(float) v1.getDomain().size()/v1.getDynamicDegree();
       float DomsizeToForwarddegree2=(float) v2.getDomain().size()/v2.getDynamicDegree();

       if(DomsizeToForwarddegree1>DomsizeToForwarddegree2) return 1;
       else if(DomsizeToForwarddegree1<DomsizeToForwarddegree2) return -1;
       return 0;

    }
}
