package formatobjects;

import java.util.Comparator;

public class BottomLayerFirstComparator implements Comparator<FormatObject>
{

	@Override
	public int compare(FormatObject o1, FormatObject o2) {
		
		return o1.getZ() - o2.getZ();
	}

}
