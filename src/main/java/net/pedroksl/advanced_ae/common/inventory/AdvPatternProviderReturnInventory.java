package net.pedroksl.advanced_ae.common.inventory;

import java.util.Set;

import net.pedroksl.advanced_ae.api.AAESettings;
import net.pedroksl.advanced_ae.common.logic.AdvPatternProviderLogic;

import appeng.api.config.YesNo;
import appeng.api.stacks.AEKey;
import appeng.helpers.patternprovider.PatternProviderReturnInventory;

public class AdvPatternProviderReturnInventory extends PatternProviderReturnInventory {
    public AdvPatternProviderReturnInventory(Runnable listener, AdvPatternProviderLogic logic) {
        super(listener);

        this.setFilter((slot, what) -> {
            var filter = logic.getConfigManager().getSetting(AAESettings.FILTERED_IMPORT);
            if (filter != YesNo.YES) return true;

            // Option A: only accept keys that are expected outputs of patterns currently
            // being executed by this provider. When no craft is active (set is empty) we
            // fall through and allow everything so the provider behaves normally when idle.
            Set<AEKey> activeOutputs = logic.getActiveExpectedOutputs();
            if (activeOutputs.isEmpty()) {
                return true;
            }
            return activeOutputs.contains(what);
        });
    }
}
