package com.luka.berry.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luka.berry.model.UnsplashPhoto
import com.luka.berry.network.UnsplashApi
import retrofit2.HttpException
import java.io.IOException


private const val UNSPLASH_STARTING_PAGE_INDEX = 1;

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val querry: String,
) : PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: 1

        return try {
            val response = unsplashApi.searchPhotos(querry, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e);

        } catch (e: HttpException) {
            LoadResult.Error(e);
        }
    }
}
