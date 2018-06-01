package stress.primitives;

public class Portico {
    private Node _node1;
    private Node _node2;

    public Portico(Node node1, Node node2) {
        this._node1 = node1;
        this._node2 = node2;
    }

    public Node get_node1() {
        return _node1;
    }

    public void set_node1(Node _node1) {
        this._node1 = _node1;
    }

    public Node get_node2() {
        return _node2;
    }

    public void set_node2(Node _node2) {
        this._node2 = _node2;
    }
}
