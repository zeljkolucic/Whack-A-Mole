package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Krtica extends Zivotinja {

	private Color boja = Color.DARK_GRAY;
	
	public Krtica(Rupa rupa) {
		super(rupa);
	}
	
	@Override
	public void iscrtaj(int x, int y, int sirina, int visina) {
		Graphics g = rupa.getGraphics();
		g.setColor(boja);
		g.fillOval(x, y, sirina, visina);
	}

	@Override
	public void udariZivotinju() {
		rupa.zaustavi();
	}

	@Override
	public boolean pobeglaZivotinja() {
		return !udarena;
	}

}
