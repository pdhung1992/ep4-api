package jsb.ep4api.services;

import jsb.ep4api.repositories.ReviewReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewReactionService {
    @Autowired
    private ReviewReactionRepository reviewReactionRepository;
}
