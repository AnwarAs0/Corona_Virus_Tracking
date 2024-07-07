package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.LocationStates;
import com.example.demo.services.CoronaVirusDataServices;
import com.example.demo.services.CoronaVirusDataServicesRepository;

@Controller
public class HomeController 
{
	@Autowired
	CoronaVirusDataServices crnService;
	
	@Autowired
	CoronaVirusDataServicesRepository repository; 
	
	@GetMapping("/")
	public String home(Model model)
	{
		List<LocationStates> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allstates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		return "home";
	}
	
	@RequestMapping(path="/collectChartData", produces = {"application/json"})
	@ResponseBody
	public List<LocationStates> collectChartData(Model m){
		List<LocationStates> allstates = crnService.getAllstates();
		int totalDeathsReported = allstates.stream().mapToInt(state->state.getLatestTotalDeaths()).sum();
		m.addAttribute("LocationStates", allstates);
		m.addAttribute("totalDeathsReported", totalDeathsReported);
		return allstates;
	}

	@RequestMapping(path = "/collectChartData/top={count}", produces = {"application/json"})
    @ResponseBody
    public List<LocationStates> collectChartDataByCountryTop(@PathVariable("count") int count) {
        System.out.println("Here view chart data by country name...");
        List<LocationStates> locationStates = repository.findTopCountriesByLatestTotalDeaths(count);
        return locationStates;
    }
	
	@RequestMapping(value = "/TopDeathChart",method = RequestMethod.GET)
	public ModelAndView TopDeathChart() {
		return new ModelAndView("TopDeathChart");
	}
	
	@RequestMapping(value = "/ViewChart",method = RequestMethod.GET)
	public ModelAndView viewChart() {
		return new ModelAndView("ViewChart");
	}
}
