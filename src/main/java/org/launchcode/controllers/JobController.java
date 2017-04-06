package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job wantedJob = null;
        wantedJob = jobData.findById(id);
        model.addAttribute("job", wantedJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute(new JobForm());
            model.addAttribute(errors);
            return "new-job";
        }

        JobData jData = JobData.getInstance();

        Employer chosenEmployer = null;
        Location chosenLocation = null;
        PositionType chosenPositionType = null;
        CoreCompetency chosenCoreCompetency = null;

        for (Employer employer : jData.getEmployers().findAll()) {
            if (employer.getId() == (jobForm.getEmployerId())) {
                chosenEmployer = employer;
            }
        }
        for (Location location : jData.getLocations().findAll()) {
            if (location.getId() == jobForm.getLocationId()) {
                chosenLocation = location;
            }
        }
        for (PositionType position : jData.getPositionTypes().findAll()) {
            if (position.getId() == jobForm.getPositionTypeId()) {
                chosenPositionType = position;
            }
        }
        for (CoreCompetency cCompet : jData.getCoreCompetencies().findAll()) {
            if (cCompet.getId() == jobForm.getCoreCompetencyId()) {
                chosenCoreCompetency = cCompet;
            }
        }

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        Job newjob = new Job( jobForm.getName(), chosenEmployer, chosenLocation, chosenPositionType, chosenCoreCompetency);
        jData.add(newjob);
        model.addAttribute("job", newjob);
        return "job-detail";

    }
}
