package interfaces;

import org.w3c.dom.Node;

public interface LevelManager
{
    public boolean isEqual(Node root1, Node root2);
    public Node generalize(Node root);
}