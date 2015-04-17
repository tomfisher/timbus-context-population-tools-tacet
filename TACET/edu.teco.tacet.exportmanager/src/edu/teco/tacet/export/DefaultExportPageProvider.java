package edu.teco.tacet.export;


public abstract class DefaultExportPageProvider implements ExportPageProvider {
    
    private TrackSelectionPage trackSelectionPage;

    @Override
    public Iterable<Long> getSelectedSensorTracks() {
        return trackSelectionPage.getSelectedSensorTracks();
    }

    @Override
    public Iterable<Long> getSelectedAnnotationTracks() {
        return trackSelectionPage.getSelectedAnnotationTracks();
    }

    public TrackSelectionPage getTrackSelectionPage() {
        return trackSelectionPage;
    }

    public void setTrackSelectionPage(TrackSelectionPage trackSelectionPage) {
        this.trackSelectionPage = trackSelectionPage;
    }

}
