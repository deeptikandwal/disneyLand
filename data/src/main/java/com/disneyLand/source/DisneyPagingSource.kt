package com.disneyLand.source

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.disneyLand.model.DisneyListCharacter
import retrofit2.HttpException
import java.io.IOException

class DisneyPagingSource(
    private val remoteDataSource: DisneyApiService,
    private val mapper: DisneyMapper
) : PagingSource<Int, DisneyListCharacter>() {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DisneyListCharacter> {
        return try {
            val currentPage = params.key ?: 1
            val charactersDto =
                remoteDataSource.fetchDisneyCharacters(page = currentPage, pageSize = PAGE_SIZE)
            var nextPageNumber: Int? = null
            if (charactersDto.info?.nextPage != null) {
                val uri = Uri.parse(charactersDto.info?.nextPage)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            LoadResult.Page(
                data = mapper.mapToDisneyCharacter(charactersDto.data),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (charactersDto.data.isEmpty()) null else nextPageNumber
            )
        } catch (exception: retrofit2.HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DisneyListCharacter>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
