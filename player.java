import java.util.*;

public class player 
{
	public char type;
	public boolean loop;
	public boolean tripod;
	public ArrayList<String> touchedEdges;

	public player(char type) 
	{
		this.type = type;
		this.loop = false;
		this.tripod = false;
		this.touchedEdges = new ArrayList<String>();
	}
}
