package controller;

import model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.Song.ISongService;

@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    ISongService songService;

    @GetMapping("")
    public String home(Model model){
        Iterable<Song> songs = songService.findAll();
        model.addAttribute("songs",songs);
        return "home";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("song", new Song());
        return "create";
    }

    @PostMapping("/create")
    public String createSong(Song song){
        songService.save(song);
        return "redirect:/songs";
    }

    @GetMapping("delete/{id}")
    public String showDeleteForm(@PathVariable("id") int id, Model model){
        Song song = songService.findById(id);
        model.addAttribute("song", song);
        return "delete";
    }

    @PostMapping("delete/{id}")
    public String deleteSong(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        Song song = songService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Song " + song.getName() +"is die");
        return "redirect:/songs";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id,Model model){
        Song song = songService.findById(id);
        model.addAttribute("song",song);
        return "edit";
    }


    @PostMapping("/edit")
    public String updateCustomer(Song song, Model model){
        songService.save(song);
        Iterable<Song> customers = songService.findAll();
        model.addAttribute("song",song);
        return "redirect:/songs";
    }


}
