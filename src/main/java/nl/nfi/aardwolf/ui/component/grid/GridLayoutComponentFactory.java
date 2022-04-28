/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.ui.component.grid;

import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;

import nl.nfi.aardwolf.ui.component.GridWrapper;
import nl.nfi.aardwolf.ui.component.SearchComponent;

public class GridLayoutComponentFactory<T, U, V> {

    /**
     * Note: method cannot be static because of generic types
     * @param mainGrid the top grid in the splitter
     * @param detailGrid the detail grid
     * @param controls a {@link SearchComponent} containing the search controls
     * @return SplitLayout that contains three grids in specified location (top, bottomLeft, bottomRight)
     */
    public SplitLayout getLayout(
                                 final GridWrapper<T> mainGrid,
                                 final GridWrapper<U> detailGrid,
                                 final SearchComponent controls) {

        final VerticalLayout bottomLayout = new VerticalLayout(detailGrid.getHeader(), detailGrid.getGrid());
        final VerticalLayout topLayout = new VerticalLayout(controls, new Hr(), mainGrid.getHeader(), mainGrid.getGrid());

        final SplitLayout masterDetailSplit = new SplitLayout(topLayout, bottomLayout);
        masterDetailSplit.setOrientation(Orientation.VERTICAL);
        masterDetailSplit.setHeight("-webkit-fill-available");
        masterDetailSplit.setSplitterPosition(50);
        return masterDetailSplit;
    }

    /**
     * Note: method cannot be static because of generic types
     * @param mainGrid the top grid in the splitter
     * @param firstDetailGrid the left detail grid
     * @param secondDetailGrid the right detail grid
     * @param controls a {@link SearchComponent} containing the search controls
     * @return SplitLayout that contains three grids in specified location (top, bottomLeft, bottomRight)
     */
    public SplitLayout getLayout(
                                 final GridWrapper<T> mainGrid,
                                 final GridWrapper<U> firstDetailGrid,
                                 final GridWrapper<V> secondDetailGrid,
                                 final SearchComponent controls) {

        final VerticalLayout bottomLayoutLeft = new VerticalLayout(firstDetailGrid.getHeader(), firstDetailGrid.getGrid());
        final VerticalLayout bottomLayoutRight = new VerticalLayout(secondDetailGrid.getHeader(), secondDetailGrid.getGrid());
        final SplitLayout detailSplit = new SplitLayout(bottomLayoutLeft, bottomLayoutRight);
        final VerticalLayout topLayout = new VerticalLayout(controls, new Hr(), mainGrid.getHeader(), mainGrid.getGrid());

        final SplitLayout masterDetailSplit = new SplitLayout(topLayout, detailSplit);
        masterDetailSplit.setOrientation(Orientation.VERTICAL);
        masterDetailSplit.setHeight("-webkit-fill-available");
        masterDetailSplit.setSplitterPosition(50);
        return masterDetailSplit;
    }
}
