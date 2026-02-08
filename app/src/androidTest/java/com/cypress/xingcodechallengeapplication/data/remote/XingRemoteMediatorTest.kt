package com.cypress.xingcodechallengeapplication.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cypress.xingcodechallengeapplication.data.local.XingDataBase
import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.data.remote.dto.Owner
import com.cypress.xingcodechallengeapplication.data.remote.dto.Permissions
import com.cypress.xingcodechallengeapplication.data.remote.dto.XingDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class XingRemoteMediatorTest {

    private lateinit var database: XingDataBase
    private lateinit var api: XingClientApi
    private lateinit var remoteMediator: XingRemoteMediator

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            XingDataBase::class.java
        ).allowMainThreadQueries().build()

        api = mockk()
        remoteMediator = XingRemoteMediator(api, database,)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun load_success() = runBlocking {
        coEvery { api.getRepo(1, 20) } returns listOf(createFakeXingDto(id = 1))

        val pagingState = PagingState<Int, XingEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Success)
    }

    fun createFakeXingDto(
        id: Int = 1,
        name: String = "Test Repo",
        fullName: String = "Test User/Test Repo"
    ): XingDto {
        return XingDto(
            allow_forking = false,
            archive_url = "",
            archived = false,
            assignees_url = "",
            blobs_url = "",
            branches_url = "",
            clone_url = "",
            collaborators_url = "",
            comments_url = "",
            commits_url = "",
            compare_url = "",
            contents_url = "",
            contributors_url = "",
            created_at = "2026-01-01T00:00:00Z",
            default_branch = "main",
            deployments_url = "",
            description = "Test description",
            disabled = false,
            downloads_url = "",
            events_url = "",
            fork = false,
            forks = 0,
            forks_count = 0,
            forks_url = "",
            full_name = fullName,
            git_commits_url = "",
            git_refs_url = "",
            git_tags_url = "",
            git_url = "",
            has_discussions = false,
            has_downloads = false,
            has_issues = true,
            has_pages = false,
            has_projects = false,
            has_wiki = false,
            hooks_url = "",
            html_url = "",
            id = id,
            is_template = false,
            issue_comment_url = "",
            issue_events_url = "",
            issues_url = "",
            keys_url = "",
            labels_url = "",
            language = "Kotlin",
            languages_url = "",
            merges_url = "",
            milestones_url = "",
            name = name,
            node_id = "",
            notifications_url = "",
            open_issues = 0,
            open_issues_count = 0,
            owner = createFakeOwner(),
            permissions = Permissions(
                admin = true,
                maintain = true,
                pull = true,
                push = true,
                triage = true
            ),
            private = false,
            pulls_url = "",
            pushed_at = "2026-01-01T00:00:00Z",
            releases_url = "",
            size = 0,
            ssh_url = "",
            stargazers_count = 0,
            stargazers_url = "",
            statuses_url = "",
            subscribers_url = "",
            subscription_url = "",
            svn_url = "",
            tags_url = "",
            teams_url = "",
            trees_url = "",
            updated_at = "2026-01-01T00:00:00Z",
            url = "",
            visibility = "public",
            watchers = 0,
            watchers_count = 0,
            web_commit_signoff_required = false
        )
    }

    fun createFakeOwner(
        id: Int = 1,
        login: String = "test_user"
    ): Owner {
        return Owner(
            avatar_url = "",
            events_url = "",
            followers_url = "",
            following_url = "",
            gists_url = "",
            gravatar_id = "",
            html_url = "",
            id = id,
            login = login,
            node_id = "",
            organizations_url = "",
            received_events_url = "",
            repos_url = "",
            site_admin = false,
            starred_url = "",
            subscriptions_url = "",
            type = "User",
            url = "",
            user_view_type = ""
        )
    }
}