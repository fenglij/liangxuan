<script setup lang="ts">
import { onMounted } from 'vue'
import { useCompanies } from './composables/useCompanies'
import NavBar from './components/NavBar.vue'
import HeroSection from './components/HeroSection.vue'
import BoardSection from './components/BoardSection.vue'
import WhySection from './components/WhySection.vue'
import CtaSection from './components/CtaSection.vue'
import FooterSection from './components/FooterSection.vue'
import AddCompanyModal from './components/AddCompanyModal.vue'
import AuthModal from './components/AuthModal.vue'
import Toast from './components/Toast.vue'

const { loadCompanies, loadUserVotes } = useCompanies()

onMounted(async () => {
  await loadCompanies()
  void loadUserVotes()
})
</script>

<template>
  <div class="relative" id="app">
    <NavBar />
    <HeroSection />
    <BoardSection />
    <WhySection />
    <CtaSection />
    <FooterSection />
    <AddCompanyModal />
    <AuthModal />
    <Toast />
  </div>
</template>
