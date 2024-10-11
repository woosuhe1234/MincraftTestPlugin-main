package Air.FourCore.nation;

import java.util.List;

public class NationBlock {
    NationBlock(List<NationData> nation, List<BlockData> block) {
        this.nation = nation;
        this.block = block;
    }

    public List<NationData> nation;
    public List<BlockData> block;
}
