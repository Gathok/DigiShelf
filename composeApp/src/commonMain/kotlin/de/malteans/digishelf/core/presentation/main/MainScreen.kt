package de.malteans.digishelf.core.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.malteans.digishelf.core.domain.SortType
import de.malteans.digishelf.core.presentation.add.AddAction
import de.malteans.digishelf.core.presentation.add.AddScreenRoot
import de.malteans.digishelf.core.presentation.add.AddViewModel
import de.malteans.digishelf.core.presentation.add.scanner.BarcodeScannerView
import de.malteans.digishelf.core.presentation.details.DetailsScreenRoot
import de.malteans.digishelf.core.presentation.main.components.CustomBookIcon
import de.malteans.digishelf.core.presentation.main.components.NavAddScreen
import de.malteans.digishelf.core.presentation.main.components.NavDetailsScreen
import de.malteans.digishelf.core.presentation.main.components.NavOverviewScreen
import de.malteans.digishelf.core.presentation.main.components.NavScannerScreen
import de.malteans.digishelf.core.presentation.main.components.NavSeriesOverviewScreen
import de.malteans.digishelf.core.presentation.main.components.NavSettingsScreen
import de.malteans.digishelf.core.presentation.main.components.NavTrashScreen
import de.malteans.digishelf.core.presentation.main.components.Screen
import de.malteans.digishelf.core.presentation.overview.OverviewAction
import de.malteans.digishelf.core.presentation.overview.OverviewScreenRoot
import de.malteans.digishelf.core.presentation.overview.OverviewViewModel
import de.malteans.digishelf.core.presentation.overview.components.SearchType
import de.malteans.digishelf.series.presentation.overview.SeriesOverviewScreenRoot
import de.malteans.digishelf.core.presentation.settings.SettingsScreenRoot
import de.malteans.digishelf.core.presentation.settings.SettingsViewModel
import de.malteans.digishelf.core.presentation.settings.components.TrashScreenRoot
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreenRoot(
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainScreen(
        state = state,
        onAction = { action -> viewModel.onAction(action) }
    )
}

@Composable
fun MainScreen(
    state: MainState,
    onAction: (MainAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))
                    NavigationDrawerItem (
                        label = { Text("Book Overview") },
                        selected = state.selectedScreen == Screen.Overview,
                        onClick = {
                            navController.popBackStack<NavOverviewScreen>(false)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        icon = {
                            Icon(
                                imageVector = CustomBookIcon,
                                contentDescription = "Book Overview"
                            )
                        },
                    )
                    NavigationDrawerItem (
                        label = { Text("Book Series") },
                        selected = state.selectedScreen == Screen.SeriesOverview,
                        onClick = {
                            navController.navigate(NavSeriesOverviewScreen)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        icon = {
                            Icon(
                                imageVector = CustomBookIcon,
                                contentDescription = "Book Series Overview"
                            )
                        },
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    NavigationDrawerItem (
                        label = { Text("Settings") },
                        selected = state.selectedScreen == Screen.Settings,
                        onClick = {
                            navController.navigate(NavSettingsScreen)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        icon = {
                            Icon(
                                imageVector = if (state.selectedScreen == Screen.Settings) Icons.Default.Settings
                                else Icons.Outlined.Settings,
                                contentDescription = "Settings"
                            )
                        },
                    )
                }
            }
        ) {
            val overviewViewModel = koinViewModel<OverviewViewModel>()
            val settingsViewModel = koinViewModel<SettingsViewModel>()

            NavHost(
                navController = navController,
                startDestination = NavOverviewScreen,
                // Set default transitions to None (individual composables below override)
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                composable<NavOverviewScreen> {
                    OverviewScreenRoot(
                        viewModel = overviewViewModel,
                        openDrawer = { scope.launch { drawerState.open() } },
                        onAddBook = { navController.navigate(NavAddScreen()) },
                        onAddBookWithScanner = { navController.navigate(NavScannerScreen) },
                        onOpenBook = { bookId -> navController.navigate(NavDetailsScreen(bookId)) }
                    )
                    onAction(MainAction.SetScreen(Screen.Overview))
                }
                composable<NavAddScreen>(
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                ) {
                    val args = it.toRoute<NavAddScreen>() // TODO: Implement AddScreen with passed isbn
                    val viewModel: AddViewModel = koinViewModel()

                    LaunchedEffect(args.isbn) {
                        if (args.isbn != null) {
                            viewModel.onAction(AddAction.OnAutoComplete(isbn = args.isbn))
                        }
                    }

                    AddScreenRoot(
                        viewModel = viewModel,
                        onShowScanner = { navController.navigate(NavScannerScreen) },
                        onShowOverview = { navController.popBackStack<NavOverviewScreen>(false) },
                        onShowBookDetail = { bookId -> navController.navigate(NavDetailsScreen(bookId)) },
                    )
                }
                composable<NavScannerScreen>(
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                ) {
                    BarcodeScannerView(
                        onBack = { navController.popBackStack() },
                        onBarcodeScanned = { isbn -> if (isbn != null) navController.navigate(NavAddScreen(isbn)) },
                    )
                }
                composable<NavDetailsScreen>(
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = 300, easing = EaseInOut)) },
                ) {
                    val args = it.toRoute<NavDetailsScreen>()
                    DetailsScreenRoot(
                        onBack = { navController.popBackStack() },
                        onAuthorSearch = { authorToSearch ->
                            overviewViewModel.onAction(
                                OverviewAction.ChangeFilterList(
                                    possessionStatus = null,
                                    readStatus = null,
                                    sortType = SortType.AUTHOR,
                                    searchType = SearchType.AUTHOR
                            ))
                            overviewViewModel.onAction(OverviewAction.SearchQueryChanged(authorToSearch))
                            navController.popBackStack<NavOverviewScreen>(false)
                        },
                        bookId = args.bookId
                    )
                    onAction(MainAction.SetScreen(Screen.Overview))
                }
                composable<NavSettingsScreen> {
                    SettingsScreenRoot(
                        viewModel = settingsViewModel,
                        openDrawer = { scope.launch { drawerState.open() } },
                        onTrashClicked = { navController.navigate(NavTrashScreen) }
                    )
                    onAction(MainAction.SetScreen(Screen.Settings))
                }
                composable<NavTrashScreen> {
                    TrashScreenRoot(
                        viewModel = settingsViewModel,
                        onBack = { navController.popBackStack() }
                    )
                    onAction(MainAction.SetScreen(Screen.Settings))
                }
                composable<NavSeriesOverviewScreen> {
                    SeriesOverviewScreenRoot(
                        openDrawer = { scope.launch { drawerState.open() } }
                    )
                    onAction(MainAction.SetScreen(Screen.SeriesOverview))
                }
            }
        }
    }
}