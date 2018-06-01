package stress.primitives;

import frames.primitives.Vector;

public class Point {
    private Vector _position;

    public Point(Vector position) {
        this._position = position;
    }

    public Vector get_position() {
        return _position;
    }

    public void set_position(Vector _position) {
        this._position = _position;
    }
}
