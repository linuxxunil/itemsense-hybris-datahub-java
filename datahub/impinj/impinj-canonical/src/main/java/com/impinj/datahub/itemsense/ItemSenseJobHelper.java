package com.impinj.datahub.itemsense;

import com.impinj.itemsense.client.coordinator.facility.Facility;
import com.impinj.itemsense.client.coordinator.job.JobResponse;
import com.impinj.itemsense.client.coordinator.job.JobStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ItemSenseJobHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger (ItemSenseJobHelper.class.getName ());


	public ItemSenseJobHelper() {
	}

	public static boolean isJobRunning(ItemSenseConnection itemSenseConnection, String facility) {

//		List<JobResponse> jobResponseList = itemSenseConnection.getCoordinatorController ().getJobController ().getJobs ()
//				.stream ()
//				.filter (jr -> jr.getStatus ().equals (JobStatus.RUNNING))
//				.collect (Collectors.toList ());

		List<JobResponse> jobResponseList = itemSenseConnection.getCoordinatorController ().getJobController ().getJobs ();

		if (jobResponseList == null || jobResponseList.size () == 0) {
			return false;
		}

		for (JobResponse jr : jobResponseList) {
		    if (jr.getStatus ().equals (JobStatus.RUNNING)) {
			    if (facility == null || facility.trim ().length () == 0) {
				    // no facility specified then any job running will suffice...
				    return true;
			    }
			    // if facility is specified make sure the match (ignore case)
			    for (Facility f : jr.getFacilities ()) {
				    if (facility.equalsIgnoreCase (f.getName ())) {
					    return true;
				    }
			    }

		    }
		}

		// Nothing matched so a job must not be running in the correct facility...
		return false;
	}
}
