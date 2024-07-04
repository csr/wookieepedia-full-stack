import { test, expect } from '@playwright/test';

test('has header', async ({ page }) => {
  // The frontend app and the backend app should be running
  await page.goto('http://localhost:3000');

  // The title should be visible
  await expect(page).toHaveTitle(/Wookieepedia/);

  // Both people and planets tabs should be visible
  await expect(page.getByRole('tab', { name: '🤖 PEOPLE' })).toBeVisible();
  await expect(page.getByRole('tab', { name: '🪐 PLANETS' })).toBeVisible();
});

test('display people data and search functionality', async ({ page }) => {
  await page.goto('http://localhost:3000');

  // Luke Skywalker should be visible
  await expect(page.getByText('Luke Skywalker')).toBeVisible();
  await expect(page.getByText('Gasgano')).not.toBeVisible();

  const peopleSearchBar = page.getByPlaceholder('Search people...');
  await peopleSearchBar.fill("gano");

  // Gasgano should be visible while Luke Skywalker is not
  await expect(page.getByText('Gasgano')).toBeVisible();
  await expect(page.getByText('Luke Skywalker')).not.toBeVisible();
});

test('display planets data and search functionality', async ({ page }) => {
  await page.goto('http://localhost:3000');

  await page.getByRole('tab', { name: '🪐 PLANETS' }).click();

  await expect(page.getByText('Tatooine')).toBeVisible();
  await expect(page.getByText('Felucia')).not.toBeVisible();

  const planetsSearchBar = page.getByPlaceholder('Search planets...');
  await planetsSearchBar.fill("fe");

  // Expect Felucia to be visible
  await expect(page.getByText('Felucia')).toBeVisible();
  await expect(page.getByText('Tatooine')).not.toBeVisible();
});

test('switching tabs should maintain search bar state', async ({ page }) => {
  await page.goto('http://localhost:3000');

  const peopleSearchBar = page.getByPlaceholder('Search people...');
  await peopleSearchBar.fill("hello");

  await page.getByRole('tab', { name: '🪐 PLANETS' }).click();

  const planetsSearchBar = page.getByPlaceholder('Search planets...');
  await planetsSearchBar.fill("world");

  // Go back to first tab ("people") and check if the search bar input is still there
  await page.getByRole('tab', { name: '🤖 PEOPLE' }).click();
  await expect(peopleSearchBar).toHaveValue("hello");

  // Go back to second tab ("planets") and check if the search bar input is still there
  await page.getByRole('tab', { name: '🪐 PLANETS' }).click();
  await expect(planetsSearchBar).toHaveValue("world");
});

test('pagination', async ({ page }) => {
  await page.goto('http://localhost:3000');

  // First page
  await expect(page.getByText('Luke Skywalker')).toBeVisible();
  await expect(page.getByText('Jabba Desilijic Tiure')).not.toBeVisible();

  // Go to second page
  await page.getByRole('button', { name: 'Next page' }).click();
  
  // Second page
  await expect(page.getByText('Jabba Desilijic Tiure')).toBeVisible();
  await expect(page.getByText('Luke Skywalker')).not.toBeVisible();
});

test('sorting', async ({ page }) => {
  await page.goto('http://localhost:3000');

  // Initial state
  await expect(page.getByText('Luke Skywalker')).toBeVisible();
  await expect(page.getByText('Ackbar')).not.toBeVisible();
  await expect(page.getByText('Zam')).not.toBeVisible();

  const nameColumnHeader = page.getByRole('columnheader', { name: 'Name'});

  await nameColumnHeader.click();

  // Sorting (ascending order)
  await expect(page.getByText('Ackbar')).toBeVisible();
  await expect(page.getByText('Luke Skywalker')).not.toBeVisible();

  // Update sort order (descending order)
  await nameColumnHeader.click();

  await expect(page.getByText('Zam')).toBeVisible();
  await expect(page.getByText('Luke Skywalker')).not.toBeVisible();
  await expect(page.getByText('Ackbar')).not.toBeVisible();
});
