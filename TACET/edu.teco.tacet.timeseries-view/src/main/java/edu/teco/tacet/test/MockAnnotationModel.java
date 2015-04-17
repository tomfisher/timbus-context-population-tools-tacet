package edu.teco.tacet.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.teco.tacet.annotation.AnnotationModel;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.DefaultIdGenerator;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.track.UniqueDataDecorator;

public class MockAnnotationModel implements AnnotationModel {
	private TrackManager tm;
	private List<Long> trackIds = new ArrayList<Long>();
	private String id;
	public MockAnnotationModel() {

		// createMockTm();
		tm = TrackManager.getInstance();

	}

	private void createMockTm() {
		DefaultIdGenerator idGen = new DefaultIdGenerator();
		TrackManager.initialise(createMockProject(), idGen, idGen,
				(Reader) null);
		tm = TrackManager.getInstance();

		for (String name : new String[] { "Temperature", "AnnoTrack2",
				"Brightness", "Contrast" }) {
			long id = tm.createAnnotationTrack(name);
			trackIds.add(id);
		}

		for (long l : trackIds)
			System.out.println("track created: " + l);
		tm.getAnnotationTrack(trackIds.get(0)).insertData(
				new Annotation(new Range(1728003000L, 15728000000L), "Test"));
		tm.getAnnotationTrack(trackIds.get(0)).insertData(
				new Annotation(new Range(15728000000L, 215728000000L), "more"));
		tm.getAnnotationTrack(trackIds.get(2)).insertData(
				new Annotation(new Range(500864000000L, 560864000000L),
						"more more"));

		// for (Annotation a : tm.getAnnotationTrack(trackIds.get(0)).getData(
		// new Range(0, 1000)))
		// System.out.println(a);
		// for (Annotation a : tm.getAnnotationTrack(trackIds.get(0)).getData(
		// new Range(0, 80)))
		// System.out.println("und " + a);
		// for (Annotation a : tm.getAnnotationTrack(trackIds.get(0)).getData(
		// new Range(20, 80)))
		// System.out.println("und2 " + a);
		// for (Annotation a : tm.getAnnotationTrack(trackIds.get(0)).getData(
		// new Range(20, 120)))
		// System.out.println("und3 " + a);
	}

	private Project createMockProject() {
		MetaFactory factory = MetaFactory.eINSTANCE;
		Project project = factory.createProject();
		return project;
	}

	@Override
	public Iterable<? extends Annotation> getAnnotations(Range range, int track) {
		if (hasTracksToShow())
			return tm.getAnnotationTrack(getTrackId(track)).getData(range);
		else
			return Collections.emptyList();
	}

	@Override
	public void deleteAnnotations(Range range, int track) {
		if (hasTracksToShow())
			tm.getAnnotationTrack(getTrackId(track)).deleteData(range);
	}

	@Override
	public boolean insertAnnotation(Annotation anno, int track) {
		if (canInsertAt(anno.getRange(), track)) {
			tm.getAnnotationTrack(this.getTrackId(track)).insertData(anno);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Annotation getAnnotation(long timestamp, int track) {
		Annotation out = null;
		if (hasTracksToShow()) {
			Iterator<? extends Annotation> it = tm.getAnnotationTrack(getTrackId(track))
					.getData(Range.point(timestamp)).iterator();
			if (it.hasNext())
				out = it.next();
		}
		return out;
	}

	@Override
	public boolean canInsertAt(Range range, int track) {
		if (hasTracksToShow()) {
			Iterator<? extends Annotation> annos = tm
					.getAnnotationTrack(getTrackId(track)).getData(range)
					.iterator();
			boolean yesWeCan = true;
			while (yesWeCan && annos.hasNext()) {
				if (annos.next() != null) {
					yesWeCan = false;
				}
			}
			return yesWeCan;
		} else {
			return false;
		}

	}

	@Override
	public boolean hasOverlap(Range range, int track) {
		boolean res = false;
		for (Annotation anno : getAnnotations(range, track)) {
			res = !(range.contains(anno.getRange()) || anno.getRange()
					.contains(range));
			if (res) {
				return res;
			}
		}
		return res;
	}

	@Override
	public boolean replace(Annotation anno, int track) {
		if (hasOverlap(anno.getRange(), track))
			return false;
		deleteAnnotations(anno.getRange(), track);
		insertAnnotation(anno, track);
		return true;
	}

	public int getNoTracks() {
		return this.trackIds.size();
	}

	public long getTrackId(int trackNo) {
		return this.trackIds.get(trackNo);
	}

	public boolean hasTracksToShow() {
		return trackIds.isEmpty() ? false : true;
	}

	@Override
	public void showTracks(Iterable<Long> ids) {
		this.trackIds = new ArrayList<Long>();
		for (long id : ids)
			this.trackIds.add(id);
	}

	@Override
	public Iterable<Long> getVisibleTracks() {
		return this.trackIds;
	}

	@Override
	public String getTrackName(int track) {
		return this.tm.getAnnotationTrack(trackIds.get(track)).getMetaData()
				.getName();
	}

	@Override
	public String[] getNameSuggestions(int trackNo) {
		UniqueDataDecorator<Annotation> udd = new UniqueDataDecorator<Annotation>(
				tm.getAnnotationTrack(trackIds.get(trackNo)),
				Annotation.LABEL_COMPARATOR);
		udd.force();
		Set<Annotation> uns = udd.getUniqueData();
		String[] names = new String[uns.size()];
		int i = 0;
		for (Annotation a : uns) {
			names[i] = a.getLabel();
			i++;
		}
		return names;
	}

	@Override
	public void setOwnID(String id) {
		this.id = id;
		
	}

	@Override
	public String getOwnID() {
		return id;
	}

	@Override
	public void setDisplayerName(String name) {
		System.out.println("Shoul not called from Marc, i dont know why mock model implements this interface");
		
	}

}
