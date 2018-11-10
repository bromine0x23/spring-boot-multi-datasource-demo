package cn.bromine0x23.demo.controllers;

import cn.bromine0x23.demo.services.PrimaryService;
import cn.bromine0x23.demo.services.SecondaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo controller.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@RestController
@RequestMapping("/version")
public class VersionController {

	private final PrimaryService primaryService;

	private final SecondaryService secondaryService;

	public VersionController(
		PrimaryService primaryService,
		SecondaryService secondaryService
	) {
		this.primaryService = primaryService;
		this.secondaryService = secondaryService;
	}

	@GetMapping("primary")
	public String primary() {
		return primaryService.version();
	}

	@GetMapping("secondary")
	public String secondary() {
		return secondaryService.version();
	}
}
