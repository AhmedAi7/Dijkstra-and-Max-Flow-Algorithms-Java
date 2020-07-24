package models;

public class Edge {
    private Node one;
    private Node two;
    private int weight = 1;
    private int weight2 = 1;
    double  flow;
    public Edge(Node one, Node two){
        this.one = one;
        this.two = two;
    }

    public Node getNodeOne(){
        return one;
    }

    public Node getNodeTwo(){
        return two;
    }

    public void setWeight(int weight){
        this.weight = weight;
        this.weight2=weight;
    }

    public int getWeight(){
        return weight;
    }

    public boolean hasNode(Node node){
        return one==node || two==node;
    }

    public boolean equals(Edge edge) {
        return (one ==edge.one && two ==edge.two) || (one ==edge.two && two ==edge.one) ;
    }

    @Override
    public String toString() {
        return "Edge ~ "
                + getNodeOne().getId() + " - "
                + getNodeTwo().getId()+ " ("+ getWeight()+")";
    }
    public int getFrom() { 
        return one.getId(); 
    } 

    public int getTo() { 
        return two.getId(); 
    } 

    public void setFlow(int flow) {
        if(flow < 0){ 
            this.flow = 0;
        } 
        else { 
            this.flow = flow; 
        } 
    } 

    public  int other(int vertex){
        if(vertex==one.getId())return two.getId(); else return one.getId();
    }
    
  
    
    public double flow(){ return flow; }
    
   public double residualCapacityTo(int vertex){
        if(vertex==one.getId())return flow; else return (weight-flow);
    } 
    
    public void increaseFlowTo(int vertex, double delta){
        if(vertex==one.getId())flow = flow-delta;
        else flow = flow+delta;
    }
    public void clear()
    {
    	flow=0.0;
    }
    
}
     


