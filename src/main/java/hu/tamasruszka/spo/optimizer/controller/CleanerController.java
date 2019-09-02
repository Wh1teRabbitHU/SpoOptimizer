package hu.tamasruszka.spo.optimizer.controller;

import hu.tamasruszka.spo.optimizer.model.Room;
import hu.tamasruszka.spo.optimizer.model.StructureInformation;
import hu.tamasruszka.spo.optimizer.service.CleanerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST api endpoints which provides tools to our 'Cleaner' partners
 */
@RestController
@RequestMapping("/cleaner")
public class CleanerController {

    private final CleanerService cleanerService;

    /**
     * Constructor for dependency injection
     *
     * @param cleanerService The autowired cleaner service instance
     */
    public CleanerController(CleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    /**
     * This endpoint calculate the optimal amount of workers for cleaning rooms at our clients.
     *
     * @param information Information object about the clients room size and the janitors skill levels.
     * @return The optimal worker count list separated by rooms
     */
    @PostMapping("/optimization")
    public List<Room> optimization(@RequestBody StructureInformation information) {
        return cleanerService.doOptimization(information);
    }

}
