package com.cypress.xingcodechallengeapplication.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.cypress.xingcodechallengeapplication.Screen
import com.cypress.xingcodechallengeapplication.presentation.viewModel.XingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun XingRepoRoute(navController: NavController){

    val xingViewModel : XingViewModel = koinViewModel()
    val xingLazyPagingItems = xingViewModel.xingFlow.collectAsLazyPagingItems()

    if(xingLazyPagingItems.loadState.refresh is LoadState.Loading){

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }

    }else{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            items(

                count = xingLazyPagingItems.itemCount,
                key = { index -> xingLazyPagingItems[index]?.id ?: index }
            ){ index ->

                val item = xingLazyPagingItems[index]
                item?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.XingDetails.route
                                        .replace("{${Screen.ID_ARG}}", it.id.toString())
                                )
                            }
                            .padding(16.dp)
                    ) {
                        Text(it.description ?: "")
                    }

                    HorizontalDivider(thickness = 1.dp)
                }

            }

            item {
                if(xingLazyPagingItems.loadState.append is LoadState.Loading){
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

}