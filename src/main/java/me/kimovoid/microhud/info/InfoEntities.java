package me.kimovoid.microhud.info;

import me.kimovoid.microhud.mixin.access.RenderGlobalAccessor;

public class InfoEntities extends InfoLine {

    public InfoEntities(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        int rendered = ((RenderGlobalAccessor)mc.renderGlobal).getEntitiesRendered();
        int total = ((RenderGlobalAccessor)mc.renderGlobal).getEntitiesTotal();
        return String.format("E: %s/%s", rendered, total);
    }
}