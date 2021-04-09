package igra;

public abstract class Zivotinja {

	protected Rupa rupa;
	protected boolean udarena;
	
	public Zivotinja(Rupa rupa) {
		this.rupa = rupa;
		rupa.postaviZivotinju(this);
	}
	
	public abstract void iscrtaj(int x, int y, int sirina, int visina);
	
	public abstract void udariZivotinju();
	
	public abstract boolean pobeglaZivotinja();
	
}
