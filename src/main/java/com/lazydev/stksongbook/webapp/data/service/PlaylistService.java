package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.repository.PlaylistRepository;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlaylistService {

    private PlaylistRepository dao;
    private SongRepository songRepository;

    public Optional<Playlist> findById(Long id) {
        return dao.findById(id);
    }

    public Optional<Playlist> findPublicById(Long id) {
        return dao.findPublicById(id);
    }

    public List<Playlist> findByName(String name) {
        return dao.findAllByName(name);
    }

    public List<Playlist> findPublicByName(String name) {
        return dao.findPublicByName(name);
    }

    public List<Playlist> findByOwnerId(Long id) {
        return dao.findByOwner(id);
    }

    public List<Playlist> findPublicByOwnerId(Long id) {
        return dao.findPublicByOwner(id);
    }

    public List<Playlist> findAllPublic() {
        return dao.findAllPublic();
    }

    public List<Playlist> findAll() {
        return dao.findAll();
    }

    public Set<Song> findAllSongs(Long playlistId) {
        return songRepository.findSongsFromPlaylist(playlistId);
    }

    public Set<Song> findAllSongsFromPublic(Long playlistId) {
        return songRepository.findSongsFromPublicPlaylist(playlistId);
    }

    public Set<Song> addSongToPublic(Long playlistId, Song song) {
        songRepository.addSongToPlaylist(playlistId, song.getId());
        return songRepository.findSongsFromPublicPlaylist(playlistId);
    }

    public void deleteSongByIdFromPublic(Long playlistId, Long songId) {
        songRepository.deleteSongFromPlaylist(playlistId, songId);
    }

    public Playlist save(Playlist saveAuthor) {
        return dao.save(saveAuthor);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
