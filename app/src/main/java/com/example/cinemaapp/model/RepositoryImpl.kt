package com.example.cinemaapp.model

class RepositoryImpl : Repository {
    override fun getCinemas():MutableList<MutableList<Cinema>>{
        val cinemaList = mutableListOf<MutableList<Cinema>>()
        cinemaList.add(getPopularCinemasFromServer())
        cinemaList.add(getTopRatedCinemasFromServer())
        cinemaList.add(getNowPlayingCinemasFromServer())
        cinemaList.add(getUpcomingCinemasFromServer())
        return cinemaList
    }
    override fun getPopularCinemasFromServer() : MutableList<Cinema> {
        val dto = CinemaRepo.api.getPopularCinemas("37a33c74592d522dfdc42d090c29c4bf","ru").execute().body()
        val popularCinemaList  = mutableListOf<Cinema>()
        for(i in 0 until dto?.results?.size!!){
            popularCinemaList.add(toCinema(dto.results[i]))
        }
        return popularCinemaList
    }

    override fun getTopRatedCinemasFromServer(): MutableList<Cinema> {
        val dto = CinemaRepo.api.getTopRatedCinemas("37a33c74592d522dfdc42d090c29c4bf","ru").execute().body()
        val topRatedCinemaList  = mutableListOf<Cinema>()
        for(i in 0 until dto?.results?.size!!){
            topRatedCinemaList.add(toCinema(dto.results[i]))
        }
        return topRatedCinemaList
    }

    override fun getUpcomingCinemasFromServer(): MutableList<Cinema> {
        val dto = CinemaRepo.api.getUpcomingCinemas("37a33c74592d522dfdc42d090c29c4bf","ru").execute().body()
        val upcomingCinemaList  = mutableListOf<Cinema>()
        for(i in 0 until dto?.results?.size!!){
            upcomingCinemaList.add(toCinema(dto.results[i]))
        }
        return upcomingCinemaList
    }

    override fun getNowPlayingCinemasFromServer(): MutableList<Cinema> {
        val dto = CinemaRepo.api.getNowPlayingCinemas("37a33c74592d522dfdc42d090c29c4bf","ru").execute().body()
        val nowPlayingCinemaList  = mutableListOf<Cinema>()
        for(i in 0 until dto?.results?.size!!){
            nowPlayingCinemaList.add(toCinema(dto.results[i]))
        }
        return nowPlayingCinemaList
    }

    override fun getCinemaFromServer(id:Int): Cinema {
        val dto: CinemaDTO? = CinemaRepo.api.getCinema(id,"37a33c74592d522dfdc42d090c29c4bf","ru").execute().body()
        return Cinema(
            adult = dto?.adult,
            id = dto?.id,
            name = dto?.title,
            releaseDate = dto?.release_date,
            poster = dto?.poster_path,
            revenue = "${dto?.revenue}$",
            rating =  "${dto?.vote_average}(${dto?.vote_count})",
            duration = "${dto?.runtime} мин",
            description = dto?.overview,
        )

    }

    private fun toCinema(cinemaDTO: CinemaDTO): Cinema{
        return Cinema(
            adult = cinemaDTO.adult,
            id = cinemaDTO.id,
            name = cinemaDTO.title.toString(),
            releaseDate = cinemaDTO.release_date.toString(),
            poster = cinemaDTO.poster_path.toString(),
            rating = cinemaDTO.vote_average.toString(),
            description = cinemaDTO.overview.toString()
        )
    }


}